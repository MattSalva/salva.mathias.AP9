package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {

    private final Long loanId;
    private final Double amount;
    private final Integer payments;
    private final String toAccountNumber;

    public LoanApplicationDTO(Long loanId, Double amount, Integer payments, String toAccountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
