package com.mindhub.homebanking.services.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Integer countByTypeAndCardHolderEquals(CardType cardType, Client client) {
        return cardRepository.countByTypeAndCardHolderEquals(cardType, client);
    }

    @Override
    public Boolean existsByColorAndTypeAndCardHolderEquals(CardColor cardColor, CardType cardType, Client client) {
        return cardRepository.existsByColorAndTypeAndCardHolderEquals(cardColor, cardType, client);
    }

    @Override
    public Card findByNumber(String number) {
        return cardRepository.findByNumber(number);
    }


    @Override
    public void save(Card card) {
        cardRepository.save(card);
    }


}
