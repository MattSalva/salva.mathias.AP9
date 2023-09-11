package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    ClientLoanService clientLoanService;


    @GetMapping("/loans")
    public List<LoanDTO> showLoans(){
        return loanService.getLoans();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> applyForLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication){

        Long loanType = loanApplicationDTO.getLoanId();
        Double loanAmount = loanApplicationDTO.getAmount();
        Integer loanPayments = loanApplicationDTO.getPayments();
        String loanToAccountNumber = loanApplicationDTO.getToAccountNumber();


        if (Objects.isNull(loanType) || loanAmount.isNaN() || Objects.isNull(loanPayments) || loanToAccountNumber.isEmpty() || loanAmount == 0 || loanPayments == 0) {

            return new ResponseEntity<>("Missing/Invalid data", HttpStatus.FORBIDDEN);

        }

        Client client = clientService.findByEmail(authentication.getName());
        Account accountCredited = accountService.findByNumber(loanToAccountNumber);
        Loan loan = loanService.findById(loanApplicationDTO.getLoanId());

        //Verificar que el préstamo exista
        if (loan == null)
            return new ResponseEntity<>("The loan request does not exist", HttpStatus.FORBIDDEN);

        // Verificar que la cuenta de destino exista
        if (accountCredited == null)
            return new ResponseEntity<>("The account does not exist", HttpStatus.FORBIDDEN);

        //Verificar que la cuenta de destino pertenezca al cliente autenticado
        if (!client.getAccounts().contains(accountCredited))
            return new ResponseEntity<>("The account does not belong to this user ", HttpStatus.FORBIDDEN);

        //Verificar que el monto solicitado no exceda el monto máximo del préstamo
        if (loanAmount > loan.getMaxAmount() )
            return new ResponseEntity<>("The amount exceeds the maximum", HttpStatus.FORBIDDEN);

        //Verifica que la cantidad de cuotas se encuentre entre las disponibles del préstamo
        if (!loan.getPayments().contains(loanPayments))
            return new ResponseEntity<>("The loan does not allow for these payments", HttpStatus.FORBIDDEN);


        ClientLoan clientLoan = new ClientLoan(loanAmount, loanPayments);
        client.addClientLoan(clientLoan);
        loan.addClientLoan(clientLoan);

        Transaction loanTransaction = new Transaction(TransactionType.CREDIT, loanAmount * 1.2, loan.getName() + " loan approved", LocalDate.now());
        accountCredited.addTransaction(loanTransaction);

        accountCredited.setBalance(accountCredited.getBalance() + loanAmount);

        clientLoanService.save(clientLoan);
        transactionService.save(loanTransaction);


        return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
    }



}
