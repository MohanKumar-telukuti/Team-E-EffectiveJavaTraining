package in.conceptarchitect.finance;

import in.conceptarchitect.finance.exceptions.InsufficientBalanceException;
import in.conceptarchitect.finance.exceptions.InvalidAccountTypeException;
import in.conceptarchitect.finance.storage.AccountStorage;
import in.conceptarchitect.finance.storage.Processor;

public class Bank {
	
	String name;
	double interestRate;
	AccountStorage storage;
	
	public Bank(AccountStorage storage,String name, double interestRate) {
		super();
		this.storage=storage;
		setInterestRate(interestRate);
		setName(name);
	}
	
	public int openAccount(String accountType,String name, String password, double amount) {
		BankAccount account= null;
		switch(accountType.toLowerCase()) {
			case "savings": account=new SavingsAccount(0,name,password,amount); break;
			case "current": account=new CurrentAccount(0,name,password,amount); break;
			case "OverDraft": account=new OverdraftAccount(0, name, password, amount); break;
			default: throw new InvalidAccountTypeException(accountType);
		}
		return storage.addAccount(account);
	}

	public void closeAccount(int accountNumber, String password) {
		var account=storage.getAccountByNumber(accountNumber);
		account.authenticate(password);
		if(account.getBalance()<0)
			throw new InsufficientBalanceException(accountNumber, - account.getBalance());
		storage.removeAccount(account);	
	}
	
	public void deposit(int accountNumber, double amount) {
		BankAccount account = storage.getAccountByNumber(accountNumber);
		account.deposit(amount);
	}

	public void withdraw(int accountNumber, double amount, String password) {
		var account=storage.getAccountByNumber(accountNumber);
		account.withdraw(amount, password);
	}		
	
	public double getAccountBalance(int accountNumber,String password) {
		BankAccount account=storage.getAccountByNumber(accountNumber);
		account.authenticate(password);
		return account.getBalance();
	}

	public void transfer(int source, double amount, String password, int target) {
		BankAccount t= storage.getAccountByNumber(target);
		BankAccount s= storage.getAccountByNumber(source);
		s.withdraw(amount,password);
		t.deposit(amount);
	}
		

	class InterestCreditor implements Processor<BankAccount>{
		@Override
		public void process(BankAccount account) {
			account.creditInterest(interestRate);
		}
	}
	
	public void creditInterst() {
		storage.process(new InterestCreditor());
	}
	
	
	public String getAccountList() {
		
		String report ="";
		final StringBuilder reportBuilder= new StringBuilder();
		storage.process(new Processor<BankAccount>() {
			@Override
			public void process(BankAccount account) {
				reportBuilder.append(account+"\n");
			}		
		}
		
		var report= reportBuilder.toString();
		return report;
	}
	
	
	public String getAccountList() {
		String report ="";
		final StringBuilder reportBuilder= new StringBuilder();
		storage.process(reportBuilder::append);
		var report= reportBuilder.toString();
		return report;
	}
	
	public int getAccountCount() {
		return storage.size();
	}
	
	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
