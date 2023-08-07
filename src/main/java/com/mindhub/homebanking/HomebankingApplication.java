package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository){
		return (args) -> {

			Client clientMelba = new Client("mmorel@outlook.com", "Melba", "Morel");
			Client clientMatt = new Client("matt@outlook.com", "Matt", "Smith");


			clientRepository.save(clientMelba);
			clientRepository.save(clientMatt);
			LocalDate currentDate = LocalDate.now();
			LocalDate tomorowDate = currentDate.plusDays(1);


			Account account1 = new Account("VIN001", 5000.0, currentDate, clientMelba);
			Account account2 = new Account("VIN002", 7500.0, tomorowDate, clientMelba);
			accountRepository.save(account1);
			accountRepository.save(account2);


		};
	}

}
