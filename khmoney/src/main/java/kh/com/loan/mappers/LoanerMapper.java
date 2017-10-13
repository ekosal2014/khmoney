package kh.com.loan.mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.solr.common.util.Hash;

import kh.com.loan.domains.Loaner;

public interface LoanerMapper {
	
	public int loanerGetMaxId();
	public int insertLoanerItem(Loaner loaner);
	public List<Loaner> loadingLoanerListInformation(HashMap<String, String> param);
	public int loadingTotalCountRows(HashMap<String, String> params);
	public Loaner loadingLoanerInformationById(HashMap<String, String> params);
	public int loanerUpdateById(Loaner loaner);
	
}
