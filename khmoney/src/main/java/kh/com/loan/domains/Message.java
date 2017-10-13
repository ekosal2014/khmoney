package kh.com.loan.domains;

public class Message {
	private String code;
	private Object objec;
	private String message;
	public Message() {};
	
	public Message(String code) {
		this.code = code;
	}
	public Message (Object object) {
		this.objec = object;
	}
	public Message(String code,Object object) {
		this.code = code;
		this.objec = object;
	}
	public Message(String code,String message) {
		this.code = code;
		this.message = message;
	}
	public Message(String code,String message,Object object) {
		this.code = code;
		this.message = message;
		this.objec = object;
	}
	public String getCode() {
		return this.code;
	}
	public Object getObject() {
		return this.objec;
	}
	
	public String getMessage() {
		return this.message;
	}
	
}
