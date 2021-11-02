package test.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.finance.Bank;

public class BankTest {
	
	//Object under Testing
	Bank bank;
	double interestRate=12;
	String correctPassword="password";
	int a1,a2;
	
	//user defined assertions
	public void assertBalanceEquals(int accountNumber, double balance) {
		assertEquals(balance, bank.getAccountBalance(accountNumber),0.01);
	}
	
	public void assertBalanceUnchanged(int accountNumber) {
		assertBalanceEquals(accountNumber, amount);
	}
	
	public void assertTransactionFailed(boolean result,int account) {
		assertFalse(result);
		assertBalanceUnchanged(account);
	}
	
	public void assertTransactionSuccess(boolean result, int account,double balance) {
		assertTrue(result);
		assertBalanceEquals(account, balance);
	}
	double amount=10000;
	int totalAccounts;
	
	@Before
	public void setup() {
		//ARRANGE
		bank=new Bank("HDFC",interestRate);
		a1=bank.openAccount("one","savings",correctPassword,amount);
		a2=bank.openAccount("one","current",correctPassword,amount);
		totalAccounts=bank.getAccountCount();
	}
	
	@Test
	public void openAccountShouldGenerateUniqueAccountNumberInAscendingSequence() {
		int newAccount=bank.openAccount("new","savings", correctPassword, amount);
		assertEquals(totalAccounts+1, newAccount);
	}
	
	//@Ignore 
	@Test
	public void openAccountShouldIncreaseTheAccountCount() {
		int newAccount=bank.openAccount("new","savings", correctPassword, amount);
		int accountCount=bank.getAccountCount();
		assertEquals(totalAccounts+1, accountCount);
		
	}
	
	//@Ignore
	@Test
	public void depositshouldWorkForValidAmountAndAccountNumber() {
		boolean result = bank.deposit(a1, 1);
		assertEquals(true,result);
		assertEquals(amount+1, bank.getAccountBalance(a1),0.01);
		assertTransactionSuccess(bank.deposit(a1, 1),	a1, amount+1);
	}
	
	//@Ignore
	@Test
	public void depositshouldFailForInvalidAmount() {
		boolean result= bank.deposit(a1, -1);
		assertFalse(result);
		assertEquals(amount, bank.getAccountBalance(a1),0.01);
		assertTransactionFailed(bank.deposit(a1, -1), a1);
	}
	
	//@Ignore
	@Test
	public void depositshouldFailForInvalidAccountNumber() {
		boolean result=bank.deposit(1000, 1);
		assertFalse(result);		
	}
	
	@Test
	public void getBalancereturnsNaNForInvalidAccountNumber() {
		assertEquals(Double.NaN, bank.getAccountBalance(-1),0.0001);
	}
	
	//@Ignore
	@Test
	public void creditInterestshouldGiveInterestToAllAccounts() {
		bank.creditInterst();
		double expectedNewBalance= amount+amount*interestRate/1200;
		assertEquals(expectedNewBalance,bank.getAccountBalance(a1), 0.01);
		assertEquals(expectedNewBalance,bank.getAccountBalance(a2),0.01);
		
	}
	
	@Ignore
	@Test
	public void withdrawshouldFailForInvalidAmount() {
		
	}
	
	@Ignore
	@Test
	public void withdrawshouldFailForInvalidAccountNumber() {
		
	}
	
	@Ignore
	@Test
	public void withdrawshouldFailForInvalidPassword() {
	
	}
	
	@Ignore
	@Test
	public void withdrawshouldFailForExcessAmount() {
	
	}
	
	//@Ignore
	@Test
	public void withdrawshouldWorkWithValidDetails() {
		boolean status = bank.transfer(a1,1,correctPassword,a2);
		assertTransactionSuccess(status, a1, amount-1);		
		assertTransactionSuccess(status, a2, amount+1);
	}
	
	//@Ignore
	@Test
	public void transfershouldFailForInvalidAmount() {
		boolean result= bank.transfer(a1, -1, correctPassword, a2);
		assertTransactionFailed(result, a1);
		assertTransactionFailed(result, a2);
	}
	
	//@Ignore
	@Test
	public void transfershouldFailForInvalidSourceAccountNumber() {
		boolean result=bank.transfer(-1, 1, correctPassword, a2);
		assertTransactionFailed(result, a2);
	}
	
	//@Ignore
	@Test
	public void transfershouldFailForInvalidTargetAccountNumber() {
		boolean result=bank.transfer(a1, 1, correctPassword, -1);
		assertFalse(result);
		assertTransactionFailed(result, a1);
	}
	
	//@Ignore
	@Test
	public void transfershouldFailForInvalidPassword() {
		var result= bank.transfer(a1, 1, "invalid-password", a2);
		assertTransactionFailed(result, a1);
		assertTransactionFailed(result, a2);
	}
	
	//@Ignore
	@Test
	public void transfershouldFailForExcessAmount() {
		var result=bank.transfer(a1, amount+1, correctPassword, a2);
		assertTransactionFailed(result, a1);
		assertTransactionFailed(result, a2);
	}
	
	//@Ignore
	@Test
	public void closeAccountshouldWorkedInHappyPath() {
		var result= bank.closeAccount(a1,correctPassword);		
		assertTrue(result);
	}
	
	@Ignore
	@Test
	public void closeAccountshouldDecreaseTheAccountCount() {
		var result=bank.closeAccount(a1,correctPassword);		
		assertEquals(totalAccounts-1, bank.getAccountCount());
	}
	
	
	//@Ignore
	@Test
	public void closeAccountshouldFailForInvalidPassword() {
		boolean result= bank.closeAccount(a1, "incorrect-password");
		assertEquals(false,result);
	}
	
	//@Ignore
	@Test
	public void closeAccountshouldFailForNegativeBalance() {
		assumeTrue(bank.getAccountBalance(a1)<0);
		
		boolean result=bank.closeAccount(a1, correctPassword);
		assertEquals(false,result);
	}
	
	//@Ignore
	@Test
	public void closeAccountshouldFailForInvalidAccountNumber() {
		boolean result=bank.closeAccount(-1, correctPassword);
		assertEquals(false,result);
	}
	
	//@Ignore
	@Test
	public void closeAccountcanotCloseSameAccountTwice() {
		boolean result=bank.closeAccount(a1, correctPassword);
		assumeTrue(result); 
		//it is closed the first time
		//proceed if the first result is true
		assertEquals(false, bank.closeAccount(a1, correctPassword));
		
	}	
}