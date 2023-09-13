package com.mindhub.homebanking.utils;

import com.mindhub.homebanking.services.AccountService;

import java.util.Random;

public final class AccountUtils {

    private AccountUtils() {
    }

    public static String accountNumberChecked(AccountService accountService){
        int maxAttempts = 10; // Límite de intentos para evitar bucles infinitos
        int attempt = 0;

        while (attempt < maxAttempts) {
            String candidateAccountNumber = AccountUtils.generateRandomAccountNumber();

            if (accountService.findByNumber(candidateAccountNumber) == null) {
                return candidateAccountNumber;
            }

            attempt++;
        }

        throw new IllegalStateException("No se pudo generar un número de cuenta único después de " + maxAttempts + " intentos.");
    }

    public static String generateRandomAccountNumber() {
        String prefix = "VIN-";
        Random random = new Random();
        int accountNumber = random.nextInt(900000) + 100000;
        return prefix + accountNumber;
    }

}
