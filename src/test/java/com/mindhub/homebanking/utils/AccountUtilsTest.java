package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
class AccountUtilsTest {

    @Autowired
    AccountService accountService;

    @Test
    void accountNumberCheckedValid() {
        String accountNumber = AccountUtils.accountNumberChecked(accountService);

        assertThat(accountNumber, startsWith("VIN"));
    }

}