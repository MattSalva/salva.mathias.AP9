package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {

    private Long id;
    private String number;
    private Double balance;
    private LocalDate date;

    private Set<TransactionDTO> transactions = new HashSet<>();

    public AccountDTO(Account account) {

        id = account.getId();
        number = account.getNumber();
        balance = account.getBalance();
        date = account.getCreationDate();
        transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());

    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Double getBalance() {
        return balance;
    }

    public LocalDate getDate() {
        return date;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
