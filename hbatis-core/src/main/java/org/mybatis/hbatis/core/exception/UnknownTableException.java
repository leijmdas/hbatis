package org.mybatis.hbatis.core.exception;

public class UnknownTableException  extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownTableException() {
		super();
	}

	public UnknownTableException(String message, Throwable cause) {
		super(message, cause);
	}

	public UnknownTableException(String message) {
		super(message);
	}

	public UnknownTableException(Throwable cause) {
		super(cause);
		
	}

}
