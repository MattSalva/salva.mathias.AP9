package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
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
    private CardRepository repo;

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType){

        Client client = clientRepository.findByEmail(authentication.getName());

        if (client.getCards().stream()
                .filter(card -> card.getType() == cardType)
                .count() >= 3)  {
            if (cardType.equals(CardType.CREDIT))
                return new ResponseEntity<>("Maximo numero de tarjetas de credito alcanzado", HttpStatus.FORBIDDEN);
            else
                return new ResponseEntity<>("Maximo numero de tarjetas de debito alcanzado", HttpStatus.FORBIDDEN);
        }




        String cardNumber = cardNumberChecked(repo);
        Client cardHolder = client;
        String cvv = Card.generateRandomCVV();
        LocalDate startDate = LocalDate.now();
        LocalDate expirationDate = startDate.plusYears(5);

        Card newCard = new Card(cardHolder, cardType, cardColor, cardNumber, cvv, expirationDate, startDate);

        repo.save(newCard);
        client.addCard(newCard);

        return new ResponseEntity<>("Tarjeta creada correctamente", HttpStatus.CREATED);

    }

    public static String cardNumberChecked(CardRepository repo){
        int maxAttempts = 10; // Límite de intentos para evitar bucles infinitos
        int attempt = 0;

        while (attempt < maxAttempts) {
            String candidateAccountNumber = Card.generateRandomCardNumber();

            if (repo.findByNumber(candidateAccountNumber) == null) {
                return candidateAccountNumber;
            }

            attempt++;
        }

        throw new IllegalStateException("No se pudo generar un número de tarjeta único después de " + maxAttempts + " intentos.");
    }

}
