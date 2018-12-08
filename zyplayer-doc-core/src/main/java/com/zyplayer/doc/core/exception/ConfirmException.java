package com.zyplayer.doc.core.exception;

/**
 * 前端提示异常
 */
public class ConfirmException extends RuntimeException {

	private static final long serialVersionUID = -7084066605197111614L;

	public ConfirmException() {
		super();
	}

	public ConfirmException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConfirmException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfirmException(String message) {
		super(message);
	}

	public ConfirmException(Throwable cause) {
		super(cause);
	}

}
