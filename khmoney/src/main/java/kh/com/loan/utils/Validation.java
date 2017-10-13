package kh.com.loan.utils;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

public class Validation {
	
	public static void isNumber(String str,String message) throws KHException {
		if (!StringUtils.isNumeric(str.replaceAll("\\P{Print}","").trim())) throw new KHException("9999",message);
	}
	
	public static void isBlank(String str,String message) throws KHException{
		if (StringUtils.isBlank(str)) throw new KHException("9999", message);
	}
	public static void isLengthCheck(String str,int number,String message) throws KHException{
		if (str.length() > number){
			throw new KHException("9999",message);
		}
	}
	public static <E extends Enum<E>> void isEnum(Class<E> clzz,String str, String message) throws KHException{
		if (!EnumUtils.isValidEnum(clzz, str)){
			throw new KHException("9999",message);
		}
	}
	
	public static void isRate(String str, String message) throws KHException {
		try {
			Double db = Double.valueOf(str);
		}catch(Exception e) {
			throw new KHException("9999", message);
		}
	}
	
}
