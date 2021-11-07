package in.conceptarchitect.finance.exceptions;

public class InsufficientBalanceException extends BankingException {
	
	double deficit;

	public double getDeficit() {
		return deficit;
	}

	public InsufficientBalanceException(int accountNumber,double deficit) {
		this(accountNumber,deficit,"Insufficient Balance");
	}

	public InsufficientBalanceException(int accountNumber, double deficit, String message) {
		super(accountNumber, message);
		this.deficit=deficit;
	}

	public InsufficientBalanceException(Throwable cause) {
		super(cause);
	}

	public InsufficientBalanceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientBalanceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}