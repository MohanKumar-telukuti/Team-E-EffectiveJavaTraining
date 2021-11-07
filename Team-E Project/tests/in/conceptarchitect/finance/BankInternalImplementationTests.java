package in.conceptarchitect.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidAccountException;
import in.conceptarchitect.finance.storage.AccountStorage;
import in.conceptarchitect.finance.storage.ArrayAccountStorage;

public class BankInternalImplementationTests {
	Bank bank;
	double interestRate=12;
	String correctPassword="password";
	int SavingAccount,CurrentAccount,OverDraft;
	
	//user defined assertions
	public void assertBalanceEquals(int accountNumber, double balance) {
		assertEquals(balance, bank.getAccountBalance(accountNumber,correctPassword),0.01);
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
		AccountStorage storage=new ArrayAccountStorage();
		bank=new Bank(storage,"HDFC",interestRate);
		SavingAccount=bank.openAccount("savings","a1",correctPassword,amount);	
		CurrentAccount=bank.openAccount("current","a2",correctPassword,amount);
		OverDraft=bank.openAccount("draft", "a3", correctPassword, amount);
		totalAccounts=bank.getAccountCount();
	}
	
	@Test
	public void getAccountByNumber_returnsValidAccountForValidAccountNumber() {
		var account=bank.storage.getAccountByNumber(SavingAccount);		
		assertNotNull(account);
	}
	
	@Test(expected=InvalidAccountException.class)
	public void getAccountByNumber_returnsNullForInvalidAccountNumber() {
		var account=bank.storage.getAccountByNumber(-1);
	}
	
	@Test(expected=InvalidAccountException.class)
	public void closeAccount_removesClosedAccount() {
		bank.closeAccount(SavingAccount, correctPassword);		
		bank.storage.getAccountByNumber(SavingAccount);
	}

	@Test(expected=InsufficientBalanceException.class)
	public void closeAccount_failsForAccountWithNegativeBalance() {
		var account=bank.storage.getAccountByNumber(SavingAccount);
		account.balance=-1;
		bank.closeAccount(SavingAccount, correctPassword);	
	}
	
	@Test
	public void accountNumbersAreAlwaysUnique() {
		bank.closeAccount(SavingAccount, correctPassword);
		int newAccount= bank.openAccount("savings","new", correctPassword, amount);
		var a2Account= bank.storage.getAccountByNumber(CurrentAccount);
		assertEquals("a2", a2Account.getName());
		assertNotEquals(CurrentAccount, newAccount);	
	}	
}