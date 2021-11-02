package in.conceptarchitect.finance;

public class BankAccount {
	private static double interestRate;
	int AccountNumber;
	String name;
	String password;
	double balance;

	public BankAccount(int AccountNumber, String name, String password, double amount) {
		
		balance=amount; //optional here we are using single balance context	
		this.name=name;
		setPassword(password);		
		this.AccountNumber=AccountNumber;
	}
	
	public int getAccountNumber() {
		return AccountNumber;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public static double getInterestRate() {
		return interestRate;
	}

	public static void setInterestRate(double interestRate) {
		
		BankAccount.interestRate = interestRate;
	}
	
	public boolean authenticate(String password) {
		return this.password.equals(salt(password));
	}

	void setPassword(String password) {
		
		this.password = salt(password);
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if(authenticate(oldPassword))
			setPassword(newPassword);
	}

	private String salt(String password) {
		String salted="";
		for(int i=0;i<password.length();i++) {
			int ch= (int) password.charAt(i);
			salted+=Integer.toHexString(ch);
		}
		return salted;
	}
	
	public void show() {
		System.out.println("account number\t"+AccountNumber);
		System.out.println("name         \t"+name);
		System.out.println("password     \t"+password);
		System.out.println("balance      \t"+balance);
		//System.out.println("interest rate\t"+Bank.getInterestRate());
		System.out.println();
	}

	public boolean deposit(double amount) {
		if(amount>0) {
			balance+=amount;
			return false;
		} else {
			return true;
		}
	}

	public boolean withdraw(double amount, String password) {
		if(amount <=0) {
			return false;
		}
		if (amount>balance) {
			return false;
		} 
		if (!this.password.equals(password))
			return false;
		else {
			balance-=amount;
			return true;
		}
	}
	
	public void creditInterest(double interestRate) {
		balance+=(balance*interestRate/1200);
	}
	
	public boolean transferTo(double amount, String password, BankAccount target){ 

		if(this.withdraw( amount, password)) {
		target.deposit(amount);
		}
		return true;
		}
}