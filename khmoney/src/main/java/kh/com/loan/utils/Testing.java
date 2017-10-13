package kh.com.loan.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;


public class Testing {
	final static String DOUBLE_PATTERN = "[0-9]{1,13}(\\\\.[0-9]*)?";
	public static void main(String[] args) throws ParseException{
		// TODO Auto-generated method stub
		
		String sourceDate = "20171007";
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		Date myDate = format.parse(sourceDate);
		myDate = DateUtils.addDays(myDate, 1);
		System.out.println(format.format(myDate));
	}

}
