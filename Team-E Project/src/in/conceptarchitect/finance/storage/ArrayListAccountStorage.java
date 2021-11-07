package in.conceptarchitect.finance.storage;

import java.util.ArrayList;

import in.conceptarchitect.finance.BankAccount;
import in.conceptarchitect.finance.exceptions.InvalidAccountException;

public class ArrayListAccountStorage implements AccountStorage {
	
	ArrayList<BankAccount> accounts=new ArrayList<>();
	int lastId;

	@Override
	public int addAccount(BankAccount account) {
		int id=++lastId;
		account.setAccountNumber(id);
		accounts.add(account);
		return id;
	}

	@Override
	public BankAccount getAccountByNumber(int accountNumber) {
		for(var account : accounts)
			if(account.getAccountNumber()==accountNumber)
				return account;
		
		throw new InvalidAccountException(accountNumber);
	}

	@Override
	public void removeAccount(BankAccount account) {
		accounts.remove(account);
	}

	@Override
	public int size() {
		return accounts.size();
	}

	@Override
	public BankAccount[] getAllAccounts() {
		BankAccount [] array=new BankAccount[size()];
		return accounts.toArray(array);
	}
}