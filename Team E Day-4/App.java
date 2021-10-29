package in.conceptarchitect.financeapp;

import java.util.Scanner;
import in.conceptarchitect.finance.BankAccount;

public class App {
	public static void main(String[] args) {
		
		String password="p@ss";
		
		//Starting Interest Rate
		System.out.println("Initial Rate:"+BankAccount.getInterestRate());
		
		BankAccount a1=new BankAccount(222, "Vivek",password, 50000);			
		BankAccount a2=new BankAccount(222,"Sanjay",password,100000);
		
		System.out.println("Before change");
		System.out.println("a1.interestRate: " + a1.getInterestRate());		
		System.out.println("a2.interestRate: "+a2.getInterestRate());
		
		
		BankAccount.setInterestRate(12);	//a1.setInterestRate(15);
		
		System.out.println("\nAfter change");
		System.out.println("a1.interestRate: " + a1.getInterestRate());		
		System.out.println("a2.interestRate: "+a2.getInterestRate());
		
	}

	private static void testAccount(BankAccount account, String password) {
		account.show();		
		account.deposit(20000);
		account.deposit(-1.0);

		account.show();
		account.creditInterest();
		account.show();

		if(account.withdraw(50000,"wrongp@ss")) {
			System.out.println("withdraw success");	
		} else {
			System.out.println("withdraw failed");
		}
		
		if(account.withdraw(-1, password)) {
			System.out.println("withdraw success");	
		} else {
			System.out.println("withdraw failed");		
		}
		
		if(account.withdraw(100000, password)){
			System.out.println("withdraw success");	
		} else {
			System.out.println("withdraw failed");		
		}
		
		if(account.withdraw(50000, password)){
			System.out.println("withdraw success");	
		} else {
			System.out.println("withdraw failed");		
		}
	}
}