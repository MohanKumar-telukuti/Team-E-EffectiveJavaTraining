package in.conceptarchitect.finance.exceptions;

public class InvalidCredentialsException extends BankingException {

	public InvalidCredentialsException(int accountNumber) {
		super(accountNumber,"Invalid Credentials");
	}

	public InvalidCredentialsException(int accountNumber, String message) {
		super(accountNumber, message);
	}

	public InvalidCredentialsException(Throwable cause) {
		super(cause);
	}

	public InvalidCredentialsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCredentialsException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}