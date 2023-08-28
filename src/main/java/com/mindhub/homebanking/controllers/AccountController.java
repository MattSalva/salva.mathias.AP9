package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountRepository repo;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return repo.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Long id, Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        Account account = repo.findById(id).orElse(null);

        if (account == null){
            return new ResponseEntity<>("Account not found", HttpStatus.BAD_GATEWAY);
        }

        if (account.getClientId().equals(client)){

            AccountDTO accountDTO = new AccountDTO(account);
            return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>("Unauthorized access to this account", HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {

        // Obtener el cliente autenticado
        Client client = clientRepository.findByEmail(authentication.getName());

        // Verificar si el cliente tiene menos de 3 cuentas registradas
        if (client.getAccounts().size() >= 3) {
            return new ResponseEntity<>("Maximo numero cuentas alcanzado", HttpStatus.FORBIDDEN);
        }

        // Crear una nueva cuenta con número aleatorio y saldo 0
        String accountNumber = accountNumberChecked(repo);

        Account newAccount = new Account(accountNumber, 0.0, LocalDate.now());
        client.addAccount(newAccount);

        // Guardar la cuenta a través del repositorio
        repo.save(newAccount);

        return new ResponseEntity<>("Cuenta creada con éxito", HttpStatus.CREATED);
    }

    public static String accountNumberChecked(AccountRepository repo){
        int maxAttempts = 10; // Límite de intentos para evitar bucles infinitos
        int attempt = 0;

        while (attempt < maxAttempts) {
            String candidateAccountNumber = Account.generateRandomAccountNumber();

            if (repo.findByNumber(candidateAccountNumber) == null) {
                return candidateAccountNumber;
            }

            attempt++;
        }

        throw new IllegalStateException("No se pudo generar un número de cuenta único después de " + maxAttempts + " intentos.");
    }

}
