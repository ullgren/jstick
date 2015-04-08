package net.jstick.api;

public class TellstickException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	
	public TellstickException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.setErrorCode(errorCode);
	}
	
	public TellstickException(int errorCode, String message) {
		this(errorCode, message, null);
	}

	public TellstickException(int errorCode, String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		this.setErrorCode(errorCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
