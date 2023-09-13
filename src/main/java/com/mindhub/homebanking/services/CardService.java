package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    Card findById(Long id);

    Integer countByTypeAndCardHolderEquals(CardType cardType, Client client);

    Boolean existsByColorAndTypeAndCardHolderEquals(CardColor cardColor, CardType cardType, Client client);

    Card findByNumber(String number);

    void save(Card card);

    void delete(Card card);
}
