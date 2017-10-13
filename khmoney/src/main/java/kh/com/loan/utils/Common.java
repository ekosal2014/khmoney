package kh.com.loan.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	private static String dtst = "yyyyMMdd";
	public String padLeft(String str, String symbol,int number) {
		if (symbol == null) {
			symbol = "0";
		}
		return null;
	}
	public String padLeft(String str,int number) {
		return padLeft(str, null, number);
	}
	public static String getCurrentDate(){
		SimpleDateFormat df = new SimpleDateFormat(dtst);
		Date dt = new Date();
		return df.format(dt).toString();
	}

}
