package in.conceptarchitect.finance;

public class CurrentAccount extends BankAccount {

	public CurrentAccount(int accountNumber, String name, String password, double amount) {
		super(accountNumber, name, password, amount);
	}
	
	@Override
	public void creditInterest(double interestRate) {
		//No interest for current account
	}

	@Override
	protected double getMaxWithdrawAmount() {
		return balance;
	}

}
