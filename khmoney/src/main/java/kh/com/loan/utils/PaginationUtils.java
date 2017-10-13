package kh.com.loan.utils;

public class PaginationUtils {
	public static int currentPage = 1;
	public static int perPage  = 10;
	public static Long total= 0l;
	public static int getStart(){
		return (currentPage * perPage) - perPage;
	}
	public static int totalPage(){
		return (int) (total / perPage) + ((total % perPage) == 0 ? 0 : 1 );
	}
}
