package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType){

        Client client = clientService.findByEmail(authentication.getName());

        if (cardService.countByTypeAndCardHolderEquals(cardType, client) >= 3 ){
            return new ResponseEntity<>("Maximo numero de tipo de tarjetas de alcanzado", HttpStatus.FORBIDDEN);
        } else if (cardService.existsByColorAndTypeAndCardHolderEquals(cardColor, cardType, client)){
            return new ResponseEntity<>("Ya existe una tarjeta de este tipo y de este color", HttpStatus.FORBIDDEN);
        }



        String cardNumber = cardNumberChecked(cardService);
        Client cardHolder = client;
        String cvv = Card.generateRandomCVV();
        LocalDate startDate = LocalDate.now();
        LocalDate expirationDate = startDate.plusYears(5);

        Card newCard = new Card(cardHolder, cardType, cardColor, cardNumber, cvv, expirationDate, startDate);

        cardService.save(newCard);
        client.addCard(newCard);

        return new ResponseEntity<>("Tarjeta creada correctamente", HttpStatus.CREATED);

    }

    public static String cardNumberChecked(CardService cardService){
        int maxAttempts = 10; // Límite de intentos para evitar bucles infinitos
        int attempt = 0;

        while (attempt < maxAttempts) {
            String candidateAccountNumber = Card.generateRandomCardNumber();

            if (cardService.findByNumber(candidateAccountNumber) == null) {
                return candidateAccountNumber;
            }

            attempt++;
        }

        throw new IllegalStateException("No se pudo generar un número de tarjeta único después de " + maxAttempts + " intentos.");
    }

}
