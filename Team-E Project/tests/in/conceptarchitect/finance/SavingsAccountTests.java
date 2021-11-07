package in.conceptarchitect.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidCredentialsException;

public class SavingsAccountTests {

	SavingsAccount account;
	double amount=20000;
	String correctPassword="pass";
	
	public SavingsAccountSpecs() {
		// TODO Auto-generated constructor stub
		account=new SavingsAccount(1, "Account", correctPassword, amount);
	}
	
	@Test
	public void savingsAccount_isATypeOfBankAccount() {
		assertTrue("SavingsAccount Not a type of BankAccount", account instanceof BankAccount);
		
	}

	@Test
	public void savingsAccount_shouldHaveMinBalance() {
		int minBalance= account.getMinBalance();
		assertEquals(5000, minBalance);
	}
	
	@Test
	public void withdraw_shouldWorkInHappyCase() {
		account.withdraw(1, correctPassword);
		assertEquals(amount-1, account.getBalance(), 0);
	}

	@Test
	public void withdraw_shouldFailIfMinBalanceIsNotMet() {
		try {
			account.withdraw(amount-account.getMinBalance()+1, correctPassword);
			fail("Withdrawal passed. Should have failed");
		}
		catch(InsufficientBalanceException ex) {
			assertEquals(1, ex.getDeficit(),0);
			assertEquals(amount, account.getBalance(),0);
		}	
	}

	@Test
	public void savingsAccount_shouldGetInterest() {
		account.creditInterest(12);
		double expectedBalance= amount+ amount*12/1200;
		assertEquals(expectedBalance, account.getBalance(),0.01);
	}

	@Test(expected=InvalidCredentialsException.class)
	public void savingsAccount_shouldFailForInvalidCredential() {
		account.withdraw(1, "wrong-password");
	}
	
	@Test
	public void toString_returnsAccountTypeNumberBalanceAndName() {	
		String expectedOutput="SavingsAccount ";
		System.out.println(account);
		assertEquals(expectedOutput, account.toString());
	}	
}