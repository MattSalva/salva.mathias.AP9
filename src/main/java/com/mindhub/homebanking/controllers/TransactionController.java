package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    AccountService accountService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam Double amount, @RequestParam String description, @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber){

        Client client = clientService.findByEmail(authentication.getName());

        if (amount.isNaN() || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (fromAccountNumber.equals(toAccountNumber)){
            return new ResponseEntity<>("Account numbers should be different", HttpStatus.FORBIDDEN);
        }

        if (accountService.findByNumber(fromAccountNumber) == null){
            return new ResponseEntity<>("Origin account does not exist", HttpStatus.FORBIDDEN);
        }

        if (accountService.findByNumber(toAccountNumber) == null){
            return new ResponseEntity<>("Recipient account does not exist", HttpStatus.FORBIDDEN);
        }

        Account originAccount = accountService.findByNumber(fromAccountNumber);
        Account recipientAccount = accountService.findByNumber(toAccountNumber);

        if (originAccount.getClientId() != client){
            return new ResponseEntity<>("Account not valid for this client", HttpStatus.FORBIDDEN);
        }

        if (originAccount.getBalance() < amount){
            return new ResponseEntity<>("Origin account has insufficient funds", HttpStatus.FORBIDDEN);
        }



        Transaction debitTransaction = new Transaction(TransactionType.DEBIT, amount, (description + " " + recipientAccount.getNumber()), LocalDate.now());
        Transaction creditTransaction = new Transaction(TransactionType.CREDIT, amount, (description + " " + originAccount.getNumber()), LocalDate.now());

        originAccount.addTransaction(debitTransaction);
        recipientAccount.addTransaction(creditTransaction);

        originAccount.setBalance(originAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);


        transactionService.save(debitTransaction);
        transactionService.save(creditTransaction);

        accountService.save(originAccount);
        accountService.save(recipientAccount);

        return new ResponseEntity<>("Transferencia realizada correctamente", HttpStatus.CREATED);


    }
}
