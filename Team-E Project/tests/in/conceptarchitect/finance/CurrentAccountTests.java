package in.conceptarchitect.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidCredentialsException;

public class CurrentAccountTests {

	CurrentAccount account;
	double amount=20000;
	String correctPassword="pass";
	public CurrentAccountSpecs() {
		// TODO Auto-generated constructor stub
		account=new CurrentAccount(1, "Account", correctPassword, amount);
	}
	
	@Test
	public void CurrentAccount_isATypeOfBankAccount() {
		assertTrue("currentAccount is Not a type of BankAccount", account instanceof BankAccount);
	}

	@Test
	public void withdraw_shouldWithdrawUptoBalance() {
		account.withdraw(1, correctPassword);
		assertEquals(amount-1, account.getBalance(), 0);
	}
	
	@Test
	public void withdraw_shouldFailForInsufficientBalance() {
		try {
			account.withdraw(amount+1, correctPassword);
			fail("Withdrawal passed. Should have failed");
		}
		catch(InsufficientBalanceException ex) {
			assertEquals(1, ex.getDeficit(),0);
			assertEquals(amount, account.getBalance(),0);
		}
	}
	
	@Test
	public void CurrentAccount_shouldNotGetInterest() {
		account.creditInterest(12);
		assertEquals(amount, account.getBalance(),0.01);
	}
}