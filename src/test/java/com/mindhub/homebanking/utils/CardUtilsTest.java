package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
class CardUtilsTest {

    @Autowired
    CardService cardService;

    @Test
    void generateRandomCVVCorrectLength() {

        String cvv = CardUtils.generateRandomCVV();

        assertThat(cvv, hasLength(3));

    }

    @Test
    void generateRandomCVVNotEmpty() {

        String cvv = CardUtils.generateRandomCVV();

        assertThat(cvv, is(not(emptyOrNullString())));

    }

    @Test
    void generateRandomCardNumberNotEmpty() {
        String cardNumber = CardUtils.generateRandomCardNumber();

        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    @Test
    void cardNumberCheckedIsValid() {
        String cardNumberChecked = CardUtils.cardNumberChecked(cardService);
        //xxxx-xxxx-xxxx-xxxx.length() == 19
        assertThat(cardNumberChecked, hasLength(19));
    }


}