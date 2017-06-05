package com.raycloud.overseas.erp.data.exception;

public class BasicException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 574794937161447026L;

	public BasicException() {
		super();
	}

	public BasicException(Throwable e) {
		super(e);
	}

	public BasicException(String prompuContent) {
		super(prompuContent);
	}
}
