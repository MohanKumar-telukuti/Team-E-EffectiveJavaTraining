package in.conceptarchitect.finance;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;

public class SavingsAccount extends BankAccount{

	public SavingsAccount(int accountNumber	, String name, String password, double amount) {
		super(accountNumber,name,password,amount);
	}

	public int getMinBalance() {
		return 5000;
	}
	
	@Override
	protected double getMaxWithdrawAmount() {
		return balance-getMinBalance();
	}
	
	@Override
	public void withdraw(double amount, String password) {		
		if(amount> getBalance()-getMinBalance())
			throw new InsufficientBalanceException(getAccountNumber(), amount-(getBalance()-getMinBalance()));
		
		super.withdraw(amount, password);
	}
}