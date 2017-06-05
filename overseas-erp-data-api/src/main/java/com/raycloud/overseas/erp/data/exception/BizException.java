package com.raycloud.overseas.erp.data.exception;

/**
 * 业务异常。
 * @date   : 2014年2月20日 下午3:44:45
 * @author : hebad90@163.com
 */
@SuppressWarnings("serial")
public class BizException extends Exception {
	
	public static final int ERROR_OCS_SESSION = 1;
	
	private int errorCode ;
	
	private String errorMsg ;
	
	public BizException() {
		super();
	}

	public BizException(Integer errorCode,String errorMsg){

		this.errorCode=errorCode;
		this.errorMsg=errorMsg;
	}
	public BizException(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public BizException(Throwable cause) {
		super(cause);
	}
	public BizException(String errorMsg, Throwable cause) {
		super(errorMsg, cause);
		this.errorMsg = errorMsg;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public BizException setErrorCode(int errorCode) {
		this.errorCode = errorCode;
		return this;
	}


}
