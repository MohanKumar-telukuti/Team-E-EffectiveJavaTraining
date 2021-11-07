package in.conceptarchitect.finance;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;

public class OverdraftAccount extends BankAccount {

	
	double odLimit;
	public OverdraftAccount(int accountNumber, String name, String password, double amount) {
		super(accountNumber, name, password, amount);
		updateOdLimit();
	}

	private void updateOdLimit() {
		if(odLimit<balance/10)
			odLimit= balance/10;
	}

	public double getOdLimit() {
		return odLimit;
	}

	@Override
	public void deposit(double amount) {
		super.deposit(amount);
		updateOdLimit();
	}
	
	@Override
	public void creditInterest(double interestRate) {
		super.creditInterest(interestRate);
		updateOdLimit();
	}
	
	@Override
	protected double getMaxWithdrawAmount() {
		return balance+odLimit;
	}
	
	@Override
	public void withdraw(double amount, String password) {
		super.withdraw(amount, password);
		if(balance<0) {
			balance-= (-balance*0.01);
		}
	}
	
	@Override
	public void withdraw(double amount, String password) {
		validateDenomination(amount);
		authenticate(password);
		if(amount > getBalance() + getOdLimit())
			throw new InsufficientBalanceException(getAccountNumber(), amount- (getBalance()+getOdLimit()));		
		balance-=amount;
		if(balance<0) {
			balance-= (-balance*0.01);
		}
	}	
}	