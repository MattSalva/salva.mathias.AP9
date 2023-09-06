package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts();

    Account findById(long id);

    Account findByNumber(String number);
    void save(Account account);


}
