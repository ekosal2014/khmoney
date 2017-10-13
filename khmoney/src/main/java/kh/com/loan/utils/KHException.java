package kh.com.loan.utils;

public class KHException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	public KHException() {};
	
	public KHException(String code) {
		this.code = code;
	}

	public KHException(String code,String message) {
		this.code = code;
		this.message = message;
	}
	public String getMessage() {
		return this.message;
	}
	public String getCode() {
		return this.code;
	}


}
