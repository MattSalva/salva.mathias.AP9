package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication){
        Client client = clientService.findByEmail(authentication.getName());
        Account account = accountService.findById(id);

        if (account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_GATEWAY);
        }

        if (account.getClient().equals(client)){

            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized access to this account", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return clientService.getAccounts(authentication.getName());
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        // Obtener el cliente autenticado
        Client client = clientService.findByEmail(authentication.getName());

        // Verificar si el cliente tiene menos de 3 cuentas registradas
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximo numero cuentas alcanzado", HttpStatus.FORBIDDEN);
        }

        // Crear una nueva cuenta con número aleatorio y saldo 0
        String accountNumber = AccountUtils.accountNumberChecked(accountService);

        Account newAccount = new Account(accountNumber, 0.0, LocalDate.now());
        client.addAccount(newAccount);

        // Guardar la cuenta a través del repositorio
        accountService.save(newAccount);

        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }



}
