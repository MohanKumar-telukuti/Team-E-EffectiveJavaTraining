package test.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import in.conceptarchitect.finance.SavingsAccount;
import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidCredentialsException;
import in.conceptarchitect.finance.exceptions.InvalidDenominationException;

public class SavingsAccountTests {

	String password="password";
	double amount=30000;
	SavingsAccount account;
	
	@Before
	public void init() {
		account =new SavingsAccount(1, "Vivek", password, amount);
		System.out.println("@Before called");
	}

	@Test public void withdrawShouldPassForValidAmountAndPassword() {
		String password="password";
		double amount=30000;
		SavingsAccount account=new SavingsAccount(1,"Vivek",password, amount);
		account.withdraw(amount, password);
		assertEquals(0, account.getBalance(),0.001);
	}
	
	@Test(expected = InvalidCredentialsException.class)
	public void withdrawShouldFailForInvalidPassword() {
		account.withdraw(1, "wrong-password");	
	}

	@Test(expected = InvalidDenominationException.class)
	public void withdrawShouldFailForNegativeAmount() {
		account.withdraw(-1, password);		
		
	}
	
	@Test public void withdrawShouldFailForExcessAmount() {
		try {
			account.withdraw(amount+1, password);
			fail("InsufficientBalanceException was expected but not thrown");
		}
		catch(InsufficientBalanceException ex) {
			assertEquals(1, ex.getDeficit(),0);
		}
	}	
}