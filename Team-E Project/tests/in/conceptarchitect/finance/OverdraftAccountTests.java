package in.conceptarchitect.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;

public class OverdraftAccountTests {
	
	OverdraftAccount account;
	double amount=10000;
	String correctPassword="pass";
	
	@Before
	public void setup() {
		account=new OverdraftAccount(1,"account",correctPassword,amount);
	}

	@Test
	public void odLimit_shouldBe10PercentOfOriginalBalance() {
		double expectedOdLimit = account.getBalance()/10;
		assertEquals(expectedOdLimit, account.getOdLimit(), 0.01);
	}

	@Test
	public void deposit_shouldUpdateOdLimitIfBalanceCrossesHistoricMaxBalance() {
		account.deposit(90000);
		double expectedOdLimit= account.getBalance()/10;
		assertEquals(expectedOdLimit, account.getOdLimit(),0.01);
		
	}

	@Test
	public void odLimit_shouldBeTenPercentOfHistoricMaxBalance() {
		//Arrange
		account.deposit(90000);
		var draftLimit = account.getOdLimit();
		account.withdraw(50000, correctPassword); //now balance reduces to 50000
		account.deposit(10000);
		assertEquals(odLimit, account.getOdLimit(),0.01);
	}
	
	@Test
	public void withdraw_shouldSucceedForAmountUptoBalancePlusdraftLimit() {
		
		account.withdraw(amount+100, correctPassword);
		assertTrue(account.getBalance()<0);
	}

	@Test()
	public void withdraw_shouldFailForAmountExceedingBalancePlusdraftLimit() {
		try {
			account.withdraw(amount+account.getOdLimit()+1, correctPassword);
			fail("withdrawal should have failed");
		}
		catch(InsufficientBalanceException ex) {
			assertEquals(1, ex.getDeficit(),0);
			assertEquals(amount,account.getBalance(),0);
		}
	}

	@Test
	public void withdraw_shouldChargeOnePercentFeeOnOdLimit() {
		double overDraft=500;
		account.withdraw(amount+overDraft, correctPassword);
		double odFee= overDraft*0.01;
		double expectedBalance = -(overDraft + odFee);
		assertEquals(expectedBalance, account.getBalance(),0.01);
	}

	@Test
	public void creditInterest_shouldCreditInterest() {
		account.creditInterest(12);
		double expectedAmount= amount+ amount/100;
		assertEquals(expectedAmount,account.getBalance(),0.01);
	}

	@Test
	public void creditInterest_shouldIncreaseOdLimitIfNeeded() {
		account.creditInterest(12);
		double expectedAmount= amount+ amount/100;
		double expectedOdLimit= expectedAmount/10;
		assertEquals(expectedOdLimit,account.getOdLimit(),0.01);
	}	
}