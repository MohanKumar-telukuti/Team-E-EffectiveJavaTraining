package in.conceptarchitect.finance.exceptions;

public class InvalidAccountTypeException extends BankingException {
	
	String accountType;

	public String getAccountType() {
		return accountType;
	}

	public InvalidAccountTypeException(String accountType) {
		this(accountType,"Invalid Account Type :"+accountType);
	}

	public InvalidAccountTypeException(String accountType, String message) {
		super(0, message);
		this.accountType=accountType;
	}

	public InvalidAccountTypeException(Throwable cause) {
		super(cause);
	}

	public InvalidAccountTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAccountTypeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}