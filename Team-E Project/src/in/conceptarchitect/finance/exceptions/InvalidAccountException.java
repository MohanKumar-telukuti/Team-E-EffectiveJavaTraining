package in.conceptarchitect.finance.exceptions;

public class InvalidAccountException extends BankingException {

	public InvalidAccountException(int accountNumber) {
		super(accountNumber,"Invalid Account Number");
	}

	public InvalidAccountException(int accountNumber, String message) {
		super(accountNumber, message);
	}

	public InvalidAccountException(Throwable cause) {
		super(cause);
	}

	public InvalidAccountException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidAccountException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}