package in.conceptarchitect.finance.storage;

import in.conceptarchitect.finance.BankAccount;
import in.conceptarchitect.finance.exceptions.InvalidAccountException;

public class ArrayAccountStorage implements AccountStorage  {
		
		int lastId=0;
		int accountCount=0;
		
		BankAccount [] accounts= new BankAccount[100];		
		@Override
		public int addAccount(BankAccount account) {
			int accountNumber= ++ lastId;
			account.setAccountNumber(accountNumber);
			accounts[accountNumber] = account; //store this account in the array.
			accountCount++;
			return accountNumber;
		}
		
		@Override
		public BankAccount getAccountByNumber(int accountNumber) {
				if(accountNumber<0 || accountNumber>lastId || accounts[accountNumber]==null)
					 throw new InvalidAccountException(accountNumber);
				
				return accounts[accountNumber];					
			}
		 
		@Override
		public void removeAccount(BankAccount account) {
			 accounts[account.getAccountNumber()]=null;
			 accountCount--;
		 }

		@Override
		public int size() {
			return accountCount;
		}
		
		@Override
		public BankAccount[] getAllAccounts() {
			return accounts;
		}

		@Override 
		public void process(Processor<BankAccount> accountProcessor) {
			if(!accountProcessor.initialize())
				return ;
				
			for(int i=0;i<=lastId;i++)
				if(accounts[i]!=null)
					accountProcessor.process(accounts[i]);
			
			accountProcessor.close();
		}		
}