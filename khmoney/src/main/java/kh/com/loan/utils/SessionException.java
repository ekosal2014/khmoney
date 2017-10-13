package kh.com.loan.utils;

public class SessionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Object objec;
	public SessionException() {};
	
	public SessionException(String code) {
		this.code = code;
	}
	public SessionException (Object object) {
		this.objec = object;
	}
	public SessionException(String code,Object object) {
		this.code = code;
		this.objec = object;
	}
	public String getCode() {
		return this.code;
	}
	public Object getObject() {
		return this.objec;
	}
}
