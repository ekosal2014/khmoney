package kh.com.loan.domains;

public class Loan {
	private int loan_id;
	private int loaner_id;
	private int user_id;
	private String start_date;
	private int count_day;
	private Long total_money;
	private Double rate;
	private String type_payment;
	private int time;
	private int decrement;
	private String type_money;
	private String sts;
	private String txt;
	private String modify_date;
	private int modify_by;
	private String action;
	
	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Loan(int loan_id, int loaner_id, int user_id, String loand_code, String start_date, int count_day,
			Long total_money, Double rate, String type_payment, int time, int decrement, String type_money,
			String sts, String txt, String modify_date, int modify_by, String action) {
		super();
		this.loan_id = loan_id;
		this.loaner_id = loaner_id;
		this.user_id = user_id;
		this.start_date = start_date;
		this.count_day = count_day;
		this.total_money = total_money;
		this.rate = rate;
		this.type_payment = type_payment;
		this.time = time;
		this.decrement = decrement;
		this.type_money = type_money;
		this.sts = sts;
		this.txt = txt;
		this.modify_date = modify_date;
		this.modify_by = modify_by;
		this.action = action;
	}
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	public int getLoaner_id() {
		return loaner_id;
	}
	public void setLoaner_id(int loaner_id) {
		this.loaner_id = loaner_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public int getCount_date() {
		return count_day;
	}
	public void setCount_date(int count_day) {
		this.count_day = count_day;
	}
	public Long getTotal_money() {
		return total_money;
	}
	public void setTotal_money(Long total_money) {
		this.total_money = total_money;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public String getType_payment() {
		return type_payment;
	}
	public void setType_payment(String type_payment) {
		this.type_payment = type_payment;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getDecrement() {
		return decrement;
	}
	public void setDecrement(int decrement) {
		this.decrement = decrement;
	}
	public String getType_money() {
		return type_money;
	}
	public void setType_money(String type_money) {
		this.type_money = type_money;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public String getTxt() {
		return txt;
	}
	public void setTxt(String txt) {
		this.txt = txt;
	}
	public String getModify_date() {
		return modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date = modify_date;
	}
	public int getModify_by() {
		return modify_by;
	}
	public void setModify_by(int modify_by) {
		this.modify_by = modify_by;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
