package in.conceptarchitect.finance.exceptions;

public class InvalidDenominationException extends BankingException {

	public InvalidDenominationException(int accountNumber) {
		super(accountNumber,"Invalid Denominations");
	}

	public InvalidDenominationException(int accountNumber, String message) {
		super(accountNumber, message);
	}

	public InvalidDenominationException(Throwable cause) {
		super(cause);
	}

	public InvalidDenominationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDenominationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}