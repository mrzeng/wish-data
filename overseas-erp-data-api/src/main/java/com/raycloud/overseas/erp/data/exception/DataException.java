package com.raycloud.overseas.erp.data.exception;

/**
 * 数据模块未知异常
 */
public class DataException extends BasicException {


	private static final long serialVersionUID = -4078732799030381071L;

	public DataException() {
		super();
	}

	public DataException(Throwable e) {
		super(e);
	}

	public DataException(String prompuContent) {
		super(prompuContent);
	}
}
