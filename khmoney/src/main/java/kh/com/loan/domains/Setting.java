package kh.com.loan.domains;

import java.math.BigDecimal;

public class Setting {
	
	private int id;
	private String columns;
	private int value;
	private double rate;
	private int day;
	private int type;
	private String sts;
	private String txt;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getColumns() {
		return columns;
	}
	public void setColumns(String columns) {
		this.columns = columns;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		BigDecimal bd = new BigDecimal(rate);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		this.rate = bd.doubleValue();
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	
	

}
