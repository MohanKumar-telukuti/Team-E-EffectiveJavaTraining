package in.conceptarchitect.finance.exceptions;

public class BankingException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	int accountNumber;
	
	public int getAccountNumber() {
		return accountNumber;
	}

	public BankingException(int accountNumber) {
		this(accountNumber, "Banking Exception");
	}

	public BankingException(int accountNumber,String message) {
		super(message);
		this.accountNumber=accountNumber;
	}

	public BankingException(Throwable cause) {
		super(cause);
	}

	public BankingException(String message, Throwable cause) {
		super(message, cause);
	}

	public BankingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}