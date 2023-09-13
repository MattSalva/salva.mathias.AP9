package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existCard(){

        List<Card> cards = cardRepository.findAll();

        assertThat(cards, is(not(empty())));

    }

    @Test
    public void existCardByClient(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, hasItem(hasProperty("cardHolder", is(notNullValue()))));
    }

    @Test
    public void existClient(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients, is(not(empty())));

    }

    @Test
    public void existAdmin(){

        List<Client> clients = clientRepository.findAll();

        assertThat(clients, hasItem(hasProperty("email", equalTo("admin@admin.com"))));

    }

    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void existsAccountByClient(){
        List<Account> accounts = accountRepository.findAll();

        assertThat(accounts, hasItem(hasProperty("client", is(notNullValue()))));
    }

    @Test
    public void transactionValidType(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(anyOf(hasProperty("type", is(TransactionType.CREDIT)), hasProperty("type", is(TransactionType.DEBIT)))));
    }

    @Test
    public void transactionByAccount(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("accountId", is(notNullValue()))));
    }


}
