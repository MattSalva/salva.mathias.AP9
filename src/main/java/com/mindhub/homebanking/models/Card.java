package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client cardHolder;

    private CardType type;

    private CardColor color;

    private String number;

    private String cvv;

    private LocalDate thruDate;
    private LocalDate fromDate;

    public Card() {
    }

    public Card(Client cardHolder, CardType type, CardColor color, String number, String cvv, LocalDate thruDate, LocalDate fromDate) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public Long getId() {
        return id;
    }

    public Client getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Client cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public static String generateRandomCVV() {
        Random random = new Random();
        int randomCVV = 100 + random.nextInt(900);
        return String.format("%03d", randomCVV);
    }

    public static String generateRandomCardNumber() {
        Random random = new Random();
        StringBuilder cardNumberBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int section = random.nextInt(10000);
            cardNumberBuilder.append(String.format("%04d", section));
            if (i < 3) {
                cardNumberBuilder.append("-");
            }
        }

        return cardNumberBuilder.toString();
    }
}
