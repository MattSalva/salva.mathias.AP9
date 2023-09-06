package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Set;

public interface ClientService {

    List<ClientDTO> getClients();

    Client findById(long id);

    ClientDTO getClient(long id);

    void save(Client client);

    ClientDTO getCurrent(String email);

    Client findByEmail(String email);

    List<AccountDTO> getAccounts(String email);

}
