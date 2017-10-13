package kh.com.loan.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.com.loan.domains.Decrement;
import kh.com.loan.domains.Loan;
import kh.com.loan.domains.LoanPayment;
import kh.com.loan.domains.Setting;

public interface LoanMapper {
	public int loanGetMaxId();
	public List<Decrement> decrementTypeValue();
	public int insertLoanItem(Loan loan);
	public int insertLoanPayment(LoanPayment loanPayment);
	public List<HashMap<String, String>> loadingLoanById(HashMap<String, String> params);
	public HashMap<String, String> loadingLoanView(HashMap<String, String> params);
	public List<Setting> loadingTypePayment();
	public List<LoanPayment> loadingLoanPayment(HashMap<String, String> params);
	public List<HashMap<String, String>> loadingLoanListView(HashMap<String, String> params);
	public int loadingLoanListViewCount(HashMap<String, String> params);
	public HashMap<String, String> loadingLoanViewEdit(HashMap<String, String> params);
	public int updateLoanById(Loan loan);
	public int deleteLoanPaymentByLoanId(@Param("loan_id") int loan_id);
	public Loan loadingLoanViewCheck(@Param("loan_id") int loan_id);
	public List<HashMap<String,String>> missingPaymentList(HashMap<String,String> params);
	public int loanPaymentSaveUpdate(HashMap<String, String> param);
	
}
