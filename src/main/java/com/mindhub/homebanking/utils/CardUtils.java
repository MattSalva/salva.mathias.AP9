package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.CardService;

import java.util.Random;

public final class CardUtils {

    private CardUtils() {
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

    public static String cardNumberChecked(CardService cardService){
        int maxAttempts = 10; // Límite de intentos para evitar bucles infinitos
        int attempt = 0;

        while (attempt < maxAttempts) {
            String candidateAccountNumber = CardUtils.generateRandomCardNumber();

            if (cardService.findByNumber(candidateAccountNumber) == null) {
                return candidateAccountNumber;
            }

            attempt++;
        }

        throw new IllegalStateException("No se pudo generar un número de tarjeta único después de " + maxAttempts + " intentos.");
    }
}
