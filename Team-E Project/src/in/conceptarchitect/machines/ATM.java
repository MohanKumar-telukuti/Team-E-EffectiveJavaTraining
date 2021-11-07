package in.conceptarchitect.finance;

public class ATM {

	Bank b=new Bank(null, 10);
	BankAccount ba=new BankAccount(1, null, null, 10);
	
	public void changePassword(String oldpassword,String newpassword) {
		ba.changePassword(oldpassword, newpassword);		
	}
	
	public void deposit(int accountNumber,double amount) {
		b.deposit(accountNumber, amount);	
	}
	
	public void withdraw(int accountNumber,double amount,String password) {
		b.withdraw(accountNumber, amount, password);
	}
}
