package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){

        return new ClientDTO(clientService.findById(id));

    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication){
        return clientService.getCurrent(authentication.getName());
    }

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if (clientService.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }

        String accountNumber = AccountController.accountNumberChecked(accountService);
        Account account = new Account(accountNumber, 0.0, LocalDate.now());

        Client newClient = new Client(email, firstName, lastName, passwordEncoder.encode(password));
        newClient.addAccount(account);
        //repo.save(newClient);
        clientService.save(newClient);
        accountService.save(account);


        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
