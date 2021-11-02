package in.conceptarchitect.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BankInternalImplementationTests {
	Bank bank;
	double interestRate=12;
	String correctPassword="password";
	int a1,a2;
	
	//user defined assertions
	public void assertBalanceEquals(int accountNumber, double balance) {
		assertEquals(balance, bank.getAccountBalance(accountNumber), 0.01);
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
		a1=bank.openAccount("a1","savings",correctPassword,amount);
		a2=bank.openAccount("a2","Current",correctPassword,amount);
		totalAccounts=bank.getAccountCount();
	}
	
	@Test
	public void getAccountByNumberreturnsValidAccountForValidAccountNumber() {
		var account=bank.getAccountByNumber(a1);		
		assertNotNull(account);
		
	}
	
	@Test
	public void getAccountByNumberreturnsNullForInvalidAccountNumber() {
		var account=bank.getAccountByNumber(-1);
		assertNull(account);
	}
	
	@Test
	public void closeAccountremovesClosedAccount() {
		bank.closeAccount(a1, correctPassword);		
		assertNull(bank.getAccountByNumber(a1));
	}

	@Test
	public void closeAccountfailsForAccountWithNegativeBalance() {
		var account=bank.getAccountByNumber(a1);
		account.balance=-1;
		var result= bank.closeAccount(a1, correctPassword);
		assertFalse(result);
		assertNotNull(bank.getAccountByNumber(a1));
	}
	
	@Test
	public void accountNumbersAreAlwaysUnique() {
		bank.closeAccount(a1, correctPassword);
		int newAccount= bank.openAccount("new","savings", correctPassword, amount);
		var a2Account= bank.getAccountByNumber(a2);
		assertEquals("a2", a2Account.getName());
		assertNotEquals(a2, newAccount);
	}
}