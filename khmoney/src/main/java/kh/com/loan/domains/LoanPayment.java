package kh.com.loan.domains;

public class LoanPayment {
	private int payment_id;
	private int loan_id;
	private String payment_date;
	private Double prak_derm;
	private Double total_rate;
	private Double total_left;
	private String txt;
	private String modify_date;
	private int modify_by;
	private String action;
	private String note;
	
	public LoanPayment() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LoanPayment(int payment_id, int loan_id, String payment_date, Double total_payment, Double prak_derm,
			Double total_rate, Double total_left, String txt, String modify_date, int modify_by, String action,
			String note) {
		super();
		this.payment_id = payment_id;
		this.loan_id = loan_id;
		this.payment_date = payment_date;
		this.prak_derm = prak_derm;
		this.total_rate = total_rate;
		this.total_left = total_left;
		this.txt = txt;
		this.modify_date = modify_date;
		this.modify_by = modify_by;
		this.action = action;
		this.note = note;
	}
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public int getLoan_id() {
		return loan_id;
	}
	public void setLoan_id(int loan_id) {
		this.loan_id = loan_id;
	}
	public String getPayment_date() {
		return payment_date;
	}
	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}
	public Double getPrak_derm() {
		return prak_derm;
	}
	public void setPrak_derm(Double prak_derm) {
		this.prak_derm = prak_derm;
	}
	public Double getTotal_rate() {
		return total_rate;
	}
	public void setTotal_rate(Double total_rate) {
		this.total_rate = total_rate;
	}
	public Double getTotal_left() {
		return total_left;
	}
	public void setTotal_left(Double total_left) {
		this.total_left = total_left;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
