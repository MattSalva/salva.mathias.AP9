package com.mindhub.homebanking;

//import com.mindhub.homebanking.controllers.AccountController;
//import com.mindhub.homebanking.models.*;
//import com.mindhub.homebanking.repositories.*;
//import com.mindhub.homebanking.services.AccountService;
//import com.mindhub.homebanking.services.ClientService;
//import com.mindhub.homebanking.services.TransactionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.password.PasswordEncoder;

//import java.time.LocalDate;
//import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);

	}

//	@Bean
//	public CommandLineRunner initData(ClientService clientService, TransactionService transactionService, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository, AccountService accountService){
//		return (args) -> {
//
//			Client clientMelba = new Client("mmorel@outlook.com", "Melba", "Morel", passwordEncoder.encode("123456"));
//			Client clientMatt = new Client("matt@outlook.com", "Matt", "Smith", passwordEncoder.encode("qwerty"));
//			Client admin = new Client("admin@admin.com", "admin", "admin", passwordEncoder.encode("123456"));
//
//			clientService.save(clientMelba);
//			clientService.save(clientMatt);
//			clientService.save(admin);
//			LocalDate currentDate = LocalDate.now();
//			LocalDate tomorowDate = currentDate.plusDays(1);
//
//
//			String accountNumber1 = AccountController.accountNumberChecked(accountService);
//			Account account1 = new Account(accountNumber1, 5000.0, currentDate);
//			clientMelba.addAccount(account1);
//
//			String accountNumber2 = AccountController.accountNumberChecked(accountService);
//			Account account2 = new Account(accountNumber2, 7500.0, tomorowDate);
//			clientMelba.addAccount(account2);
//
//			accountService.save(account1);
//			accountService.save(account2);
//
//
//
//			String accountNumber3 = AccountController.accountNumberChecked(accountService);
//			Account account3 = new Account(accountNumber3, 100000.0, currentDate);
//			clientMatt.addAccount(account3);
//
//			String accountNumber4 = AccountController.accountNumberChecked(accountService);
//			Account account4 = new Account(accountNumber4, 95000.0, tomorowDate.plusDays(7));
//			clientMatt.addAccount(account4);
//
//			accountService.save(account3);
//			accountService.save(account4);
//
//			Transaction transaction1 = new Transaction(TransactionType.CREDIT, 500.0, "Bono", tomorowDate.plusDays(2));
//			Transaction transaction2 = new Transaction(TransactionType.DEBIT, 200.0, "Impuesto", tomorowDate.plusDays(2));
//			Transaction transaction3 = new Transaction(TransactionType.CREDIT, 5000.0, "Bono", tomorowDate.plusDays(3));
//			Transaction transaction4 = new Transaction(TransactionType.DEBIT, 539.0, "Deduccion de ganancia", tomorowDate.plusDays(3));
//			Transaction transaction5 = new Transaction(TransactionType.CREDIT, 1900.0, "Venta realizada", tomorowDate.plusDays(4));
//			Transaction transaction6 = new Transaction(TransactionType.DEBIT, 190.0, "Aranceles", tomorowDate.plusDays(4));
//			Transaction transaction7 = new Transaction(TransactionType.DEBIT, 100.0, "Impuesto venta", tomorowDate.plusDays(4));
//			Transaction transaction8 = new Transaction(TransactionType.CREDIT, 100000.0, "Sueldo", tomorowDate.plusDays(5));
//			Transaction transaction9 = new Transaction(TransactionType.DEBIT, 25000.0, "Jubilacion", tomorowDate.plusDays(5));
//
//			account1.addTransaction(transaction1);
//			account1.addTransaction(transaction2);
//
//			account2.addTransaction(transaction3);
//			account2.addTransaction(transaction4);
//			account3.addTransaction(transaction5);
//			account3.addTransaction(transaction6);
//			account3.addTransaction(transaction7);
//
//			account4.addTransaction(transaction8);
//			account4.addTransaction(transaction9);
//
//			transactionService.save(transaction1);
//			transactionService.save(transaction2);
//			transactionService.save(transaction3);
//			transactionService.save(transaction4);
//			transactionService.save(transaction5);
//			transactionService.save(transaction6);
//			transactionService.save(transaction7);
//			transactionService.save(transaction8);
//			transactionService.save(transaction9);
//
//			Loan loan1 = new Loan("Hipotecario", 500000.0, List.of(12, 24, 36, 48, 60));
//			Loan loan2 = new Loan("Personal", 100000.0, List.of(6, 12, 24));
//			Loan loan3 = new Loan("Automotriz", 300000.0, List.of(6, 12, 24, 36));
//
//			loanRepository.save(loan1);
//			loanRepository.save(loan2);
//			loanRepository.save(loan3);
//
//			ClientLoan clientLoan1 = new ClientLoan(400000.0, 60);
//			clientMelba.addClientLoan(clientLoan1);
//			loan1.addClientLoan(clientLoan1);
//
//			ClientLoan clientLoan2 = new ClientLoan(50000.0, 12);
//			clientMelba.addClientLoan(clientLoan2);
//			loan2.addClientLoan(clientLoan2);
//
//			ClientLoan clientLoan3 = new ClientLoan(100000.0, 24);
//			clientMatt.addClientLoan(clientLoan3);
//			loan2.addClientLoan(clientLoan3);
//
//			ClientLoan clientLoan4 = new ClientLoan(200000.0, 36);
//			clientMatt.addClientLoan(clientLoan4);
//			loan3.addClientLoan(clientLoan4);
//
//			clientLoanRepository.save(clientLoan1);
//			clientLoanRepository.save(clientLoan2);
//			clientLoanRepository.save(clientLoan3);
//			clientLoanRepository.save(clientLoan4);
//
//			Card card1 = new Card(clientMelba, CardType.DEBIT, CardColor.GOLD, "3240-2242-1232-4425", "901", LocalDate.now().plusYears(5), LocalDate.now());
//			Card card2 = new Card(clientMelba, CardType.CREDIT, CardColor.TITANIUM, "5355-3231-1228-8821", "155", LocalDate.now().plusYears(5), LocalDate.now());
//			Card card3 = new Card(clientMatt, CardType.CREDIT, CardColor.SILVER, "1123-5531-8894-4416", "212", LocalDate.now().plusYears(8), LocalDate.now());
//
//			cardRepository.save(card1);
//			cardRepository.save(card2);
//			cardRepository.save(card3);
//
//
//
//
//
//
//
//
//		};
//	}

}
