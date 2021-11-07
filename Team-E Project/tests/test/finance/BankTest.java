package test.finance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeTrue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import in.conceptarchitect.finance.Bank;
import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidAccountException;
import in.conceptarchitect.finance.exceptions.InvalidAccountTypeException;
import in.conceptarchitect.finance.exceptions.InvalidCredentialsException;
import in.conceptarchitect.finance.storage.AccountStorage;
import in.conceptarchitect.finance.storage.ArrayAccountStorage;
import in.conceptarchitect.finance.storage.ArrayListAccountStorage;
import in.conceptarchitect.finance.storage.HashmapAccountStorage;

public class BankTest {
	
	//Object under Test
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
	
	public void assertTransactionFailed(int account) {
		assertBalanceUnchanged(account);
	}
	
	public void assertTransactionSuccess(int account,double balance) {
		assertBalanceEquals(account, balance);
	}
	
	double amount=10000;
	int totalAccounts;
	
	@Before
	public void setup() {
		//ARRANGE
		AccountStorage storage=new ArrayAccountStorage();
		AccountStorage storage=new ArrayListAccountStorage();
		var storage= new HashmapAccountStorage();
		bank=new Bank(storage,"HDFC",interestRate);
		SavingAccount=bank.openAccount("savings","one",correctPassword,amount);
		CurrentAccount=bank.openAccount("current","two",correctPassword,amount);
		OverDraft=bank.openAccount("draft", "three", correctPassword,amount);
		totalAccounts=bank.getAccountCount();
	}
	
	@Test
	public void openAccountShouldGenerateUniqueAccountNumberInAscendingSequence() {
		int newAccount=bank.openAccount("savings","new", correctPassword, amount);
		assertEquals(totalAccounts+1, newAccount);
	}

	@Test
	public void openAccountShouldIncreaseTheAccountCount() {
		int newAccount=bank.openAccount("savings","new", correctPassword, amount);
		int accountCount=bank.getAccountCount();
		assertEquals(totalAccounts+1, accountCount);
		
	}
	
	@Test(expected=InvalidAccountTypeException.class)
	public void openAccount_failsForInvalidAccountType() {
		bank.openAccount("invalid", "new", correctPassword, amount);
	}
	
	@Test
	public void creditInterest_currentAccountWillNotGetInterest() {
		bank.creditInterst();
		assertEquals(amount, bank.getAccountBalance(CurrentAccount, correctPassword),0);
	}
	
	@Test
	public void creditInterest_SavingsAccountWillGetInterest() {
		bank.creditInterst();
		double expectedBalance= amount+ amount/100;
		assertEquals(expectedBalance, bank.getAccountBalance(SavingAccount, correctPassword),0.01);
	}
	
	@Test
	public void creditInterest_OverDraftAccountWillGetInterest() {
		bank.creditInterst();
		double expectedBalance= amount+ amount/100;
		assertEquals(expectedBalance, bank.getAccountBalance(OverDraft, correctPassword),0.01);
	}
	
	@Test
	public void deposit_shouldWorkForValidAmountAndAccountNumber() {
		boolean result = bank.deposit(a1, 1);		
		assertEquals(true,result);
		assertEquals(amount+1, bank.getAccountBalance(a1),0.01);
		bank.deposit(SavingAccount, 1);
		assertTransactionSuccess(SavingAccount, amount+1);
		
	}

	@Test
	public void deposit_shouldFailForInvalidAmount() {
		bank.deposit(a1, -1)
		assertTransactionFailed a1);
	}

	@Test
	public void deposit_shouldFailForInvalidAccountNumber() {
		bank.deposit(1000, 1);
				
	}

	@Test
	public void getBalance_failsForInvalidAccountNumber() {
		assertEquals(Double.NaN, bank.getAccountBalance(-1),0.0001);
	}

	@Test
	public void creditInterest_shouldGiveInterestToAllActiveAccounts() {
		
		//ARRANGE
		bank.closeAccount(SavingAccount, correctPassword);
		
		//ACT 
		bank.creditInterst();
		double expectedInterest=amount * 0.01;
		assertEquals(amount,bank.getAccountBalance(CurrentAccount, correctPassword),0.01);
		assertEquals(amount+expectedInterest, bank.getAccountBalance(OverDraft, correctPassword),0.01);		
	}

	@Test
	public void withdraw_shouldFailForInvalidAmount() {
		
	}

	@Test
	public void withdraw_shouldFailForInvalidAccountNumber() {
		
	}

	@Test
	public void withdraw_shouldFailForInvalidPassword() {
		
	}

	@Test
	public void withdraw_shouldFailForExcessAmount() {
		
	}

	@Test
	public void transfer_shouldWorkWithValidDetails() {
		
		bank.transfer(SavingAccount,1,correctPassword,CurrentAccount);
		
		assertTransactionSuccess(SavingAccount, amount-1);		
		assertTransactionSuccess(CurrentAccount, amount+1);
	}

	@Test
	public void transfer_shouldFailForInvalidAmount() {
		bank.transfer(SavingAccount, -1, correctPassword,CurrentAccount);
		assertTransactionFailed(SavingAccount);
		assertTransactionFailed(CurrentAccount);
	}

	@Test
	public void transfer_shouldFailForInvalidSourceAccountNumber() {
		bank.transfer(-1, 1, correctPassword,CurrentAccount);
		assertTransactionFailed(CurrentAccount);
	}

	@Test(expected=InsufficientBalanceException.class)
	public void closeAccount_shouldFailForNegativeBalance() {
		
		//arrange
		bank.withdraw(OverDraft, amount+1, correctPassword); 
		assumeTrue(bank.getAccountBalance(OverDraft, correctPassword)<0);
		
		//act
		bank.closeAccount(OverDraft, correctPassword);
	}

	@Test(expected=InvalidAccountException.class)
	public void closeAccount_shouldFailForInvalidAccountNumber() {
		bank.closeAccount(-1, correctPassword);
		
	}

	@Test(expected=InvalidAccountException.class)
	public void closeAccount_cantCloseSameAccountTwice() {
		bank.closeAccount(SavingAccount,correctPassword);
		assumeTrue(true);		
		bank.closeAccount(SavingAccount,correctPassword);		
	}

	@Test
	public void transfer_shouldFailForInvalidTargetAccountNumber() {
		bank.transfer(SavingAccount, 1, correctPassword, -1);
		assertTransactionFailed(SavingAccount);
	}

	@Test
	public void transfer_shouldFailForInvalidPassword() {
		bank.transfer(SavingAccount, 1, "invalid-password",CurrentAccount);
		assertTransactionFailed(SavingAccount);
		assertTransactionFailed(CurrentAccount);
		
	}

	@Test(expected = InvalidCredentialsException.class)
	public void closeAccount_shouldFailForInvalidPassword() {
		 bank.closeAccount(SavingAccount,"incorrect-password");
		
	}

	@Test
	public void transfer_shouldFailForExcessAmount() {
		bank.transfer(SavingAccount, amount+1, correctPassword, CurrentAccount);
		assertTransactionFailed(SavingAccount);
		assertTransactionFailed(CurrentAccount);
	}

	@Test
	public void closeAccount_shouldDecreaseTheAccountCount() {
		bank.closeAccount(SavingAccount,correctPassword);		
		assertEquals(totalAccounts-1, bank.getAccountCount());
	}
}