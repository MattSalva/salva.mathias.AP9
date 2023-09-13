package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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



        String cardNumber = CardUtils.cardNumberChecked(cardService);
        Client cardHolder = client;
        String cvv = CardUtils.generateRandomCVV();
        LocalDate startDate = LocalDate.now();
        LocalDate expirationDate = startDate.plusYears(5);

        Card newCard = new Card(cardHolder, cardType, cardColor, cardNumber, cvv, expirationDate, startDate);

        cardService.save(newCard);
        client.addCard(newCard);

        return new ResponseEntity<>("Tarjeta creada correctamente", HttpStatus.CREATED);

    }

    @Transactional
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Object> deleteCard(@PathVariable Long id, Authentication authentication){
        Card card = cardService.findById(id);

        if (card == null)
            return new ResponseEntity<>("Card does not exist", HttpStatus.BAD_REQUEST);

        if (!card.getCardHolder().equals(clientService.findByEmail(authentication.getName())))
            return new ResponseEntity<>("Unauthorized access to this card", HttpStatus.FORBIDDEN);

        cardService.delete(card);
        return new ResponseEntity<>("Card deleted", HttpStatus.OK);
    }



}
