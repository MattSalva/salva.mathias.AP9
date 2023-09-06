package com.mindhub.homebanking.services;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;

public interface CardService {

    Integer countByTypeAndCardHolderEquals(CardType cardType, Client client);

    Boolean existsByColorAndTypeAndCardHolderEquals(CardColor cardColor, CardType cardType, Client client);

    Card findByNumber(String number);

    void save(Card card);
}
