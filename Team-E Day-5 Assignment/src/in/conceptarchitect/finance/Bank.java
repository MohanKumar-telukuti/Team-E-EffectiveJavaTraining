package in.conceptarchitect.finance;

public class Bank {
	
	String name;
	double interestRate;
	int lastId=0;
		
	public void creditInterst() {
		//for loop used to credit interest to all accounts in the bank
		for(int i=1;i<=lastId;i++) {
			accounts[i].creditInterest(interestRate);
		}
	}
	
	public Bank(String name, double interestRate) {
		super();
		this.name = name;
		this.interestRate = interestRate;
	}
	BankAccount [] accounts= new BankAccount[1000];
	public int openAccount(String name, String password, double amount) {
		int AccountNumber= ++ lastId;
		BankAccount account= new BankAccount(AccountNumber, name, password,amount);
		accounts[AccountNumber] = account; 
		return AccountNumber;
	}
	
	private BankAccount getAccountByNumber(int AccountNumber) {
		if(AccountNumber>0 && AccountNumber<=lastId)
			return accounts[AccountNumber];
		else
			return null;
	}
	
	public boolean deposit(int AccountNumber, double amount) {
		BankAccount account = getAccountByNumber(AccountNumber);
		return account.deposit(amount);
	}

	public boolean withdraw(int AccountNumber,double amount,String password) {
		BankAccount BA=getAccountByNumber(AccountNumber);
		return BA.withdraw(amount, password);
	}
	
	public boolean transferto(int AccountNumber,double amount,String password,BankAccount target) {
		BankAccount BA=getAccountByNumber(AccountNumber);	
		return BA.transferTo(amount, password, target);	
	}

	public void closeAccount(int AccountNumber) {
		AccountNumber=(Integer) null;
		System.out.println("Account Deleted");
		
	}
}
