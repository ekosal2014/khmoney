package kh.com.loan.services;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import kh.com.loan.domains.Loan;
import kh.com.loan.domains.LoanPayment;
import kh.com.loan.domains.Loaner;
import kh.com.loan.domains.Message;
import kh.com.loan.domains.Setting;
import kh.com.loan.domains.User;
import kh.com.loan.mappers.LoanMapper;
import kh.com.loan.mappers.LoanerMapper;
import kh.com.loan.mappers.SettingMapper;
import kh.com.loan.utils.Common;
import kh.com.loan.utils.KHException;
import kh.com.loan.utils.PaginationUtils;
import kh.com.loan.utils.Validation;

@Service
public class LoanerService {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat formatToSting = new SimpleDateFormat("dd-MM-yyyy");
	
	@Autowired
	private LoanerMapper loanerMapper;
	@Autowired
	private LoanMapper loanMapper;
	
	@Autowired
	private SettingMapper settingMapper;
	
	public Message loanerGetMaxId() throws KHException {
		HashMap<String, String> result = new HashMap<>();
		try {
			int maxLoanerId = loanerMapper.loanerGetMaxId() + 1;
			String maxLoanerPad = StringUtils.leftPad(String.valueOf(maxLoanerId), 9, "0");
			int maxLoanId = loanMapper.loanGetMaxId() + 1;
			String maxLoanPad =  StringUtils.leftPad(String.valueOf(maxLoanId), 9, "0");
			result.put("maxLoanerId", maxLoanerPad);
			result.put("maxLoanId", maxLoanPad);
			return new Message("0000", result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message loadingSettingData() throws KHException{
		HashMap<String, Object> objSetting = new HashMap<>();
		try {
			List<Setting> columnList  = (List) settingMapper.loadingListColumns();
			List<Setting> settingList = (List) settingMapper.loadingListSetting();	
			objSetting.put("ListColumns", columnList);
			objSetting.put("settingList", settingList);
			return new Message("0000",objSetting);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message loadingSettingValue() throws KHException{
		HashMap<String, Object> objSetting = new HashMap<>();
		try {			
			List<Setting> settingList = (List) settingMapper.loadingListSetting();				
			objSetting.put("settingList", settingList);
			return new Message("0000",objSetting);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message decrementTypeValue() throws KHException {
		HashMap<String, Object> objSetting = new HashMap<>();
		try {			
			List<Setting> decrementList = (List) loanMapper.decrementTypeValue();				
			objSetting.put("decrementList", decrementList);
			return new Message("0000",objSetting);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Message loanSaveNewItem(Map map) throws KHException {
		/*Loaner information */
		Validation.isNumber((String)map.get("id_card"), "id card");
		Validation.isNumber((String)map.get("phone"), "phone");
		Validation.isBlank((String)map.get("address"), "address");
		Validation.isBlank((String)map.get("loaner_name"), "loaner_name");
		/*Validation.isEnum(Gender.class, (String)map.get("gender"), "gender");*/
/*		if (EnumUtils.isValidEnum(Gender.class, (String)map.get("gender")) != true) {
			throw new KHException("9999", "gender");
		}*/
		/*loan information*/
		Validation.isNumber((String)map.get("total_money"), "total");
		Validation.isNumber((String)map.get("decrement"), "decrement");
		Validation.isNumber((String)map.get("time"), "time");		
		Validation.isRate((String)map.get("rate"), "rate");
		User user = new User();
		Loaner loaner = new Loaner();
		Loan loan = new Loan();
		try {
			
			Long total_money= Long.valueOf((String)map.get("total_money"));
			int  decrement  = Integer.valueOf((String)map.get("decrement"));
			Double rate     = Double.valueOf((String)map.get("rate"));
			int time        = Integer.valueOf((String)map.get("time"));
			int  day        = Integer.valueOf((String)map.get("day"));
			Double prak_derm  = (double) (total_money / time);
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!auth.getPrincipal().equals("anonymousUser")) {
				user = (User) auth.getPrincipal();
				
			}else {
				throw new KHException("9999", "user id");
			}
			
			loaner.setUser_id(user.getUser_id());
			loaner.setLoaner_name((String)map.get("loaner_name"));
			loaner.setGender((String)map.get("gender"));
			loaner.setPhone((String)map.get("phone"));
			loaner.setId_card((String)map.get("id_card"));
			loaner.setAddress((String)map.get("address"));
			loaner.setSts("1");
			loaner.setTxt("1");
			loaner.setModify_by(user.getUser_id());
			loaner.setAction("insert Loaner");
			loaner.setModify_date(Common.getCurrentDate());
			
			
			
			loanerMapper.insertLoanerItem(loaner);

			
			loan.setLoaner_id(loaner.getLoaner_id());
			loan.setTotal_money(total_money);
			loan.setUser_id(user.getUser_id());
			loan.setStart_date((String)map.get("start_date"));
			loan.setCount_date(day);
			loan.setRate(rate);
			loan.setType_payment((String)map.get("type_payment"));
			loan.setType_money("1");
			loan.setTime(time);
			loan.setDecrement(decrement);
			loan.setSts("1");
			loan.setTxt("1");
			loan.setModify_by(user.getUser_id());
			loan.setModify_date(Common.getCurrentDate());
			loan.setAction("inser Loan");
					
			
			loanMapper.insertLoanItem(loan);
			
			Date dt = formatter.parse((String)map.get("start_date"));
			
			
			for(int i=1; i<= Integer.valueOf((String)map.get("time")) ; i++) {
				LoanPayment loanPayment = new LoanPayment();
				dt = DateUtils.addDays(dt, day);
				
				Double total_rate = 0.0;
				if (decrement == 0) {
					total_rate = (Double) ((prak_derm * rate) / 100); 
				}else {
					total_rate = (Double) ((total_money * rate) / 100); 
				}

				Double total_left = total_money - (prak_derm * i) ;
				
				
				loanPayment.setLoan_id(loan.getLoan_id());
				loanPayment.setPayment_date(formatter.format(dt).toString());
				loanPayment.setPrak_derm(prak_derm);
				loanPayment.setTotal_rate(total_rate);
				loanPayment.setTotal_left(total_left);
				loanPayment.setTxt("1");
				loanPayment.setModify_by(user.getUser_id());
				loanPayment.setModify_date(Common.getCurrentDate());
				loanPayment.setAction("Insert Loaner payment");
				loanMapper.insertLoanPayment(loanPayment);
			}

			return new Message("0000","successfull");
		}catch(Exception e) {
			throw new KHException("9999",e.getMessage());
		}
	}
	public Message loadingLoanerListInformation(HashMap<String, String> params) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		try {
			
			PaginationUtils.perPage = Integer.valueOf(params.get("perPage"));
			PaginationUtils.currentPage = Integer.valueOf(params.get("currentPage"));
			PaginationUtils.total = (long) loanerMapper.loadingTotalCountRows(params);
			params.put("search", (String)params.get("search").trim());
			params.put("start",  String.valueOf(PaginationUtils.getStart()));
			params.put("perPage", String.valueOf(PaginationUtils.perPage));	
			result.put("loanerList", loanerMapper.loadingLoanerListInformation(params));
			result.put("loadingTotalCountRows", PaginationUtils.total);
			result.put("totalPage", PaginationUtils.totalPage());			
			return new Message("0000",result);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message loadingLoanAgain(int loaner_id) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, String> params = new HashMap<>();
		try {
			int maxLoanId = loanMapper.loanGetMaxId() + 1;
			String maxLoanPad =  StringUtils.leftPad(String.valueOf(maxLoanId), 9, "0");
			result.put("maxLoanId", maxLoanPad);
			params.put("loaner_id", String.valueOf(loaner_id));
			params.put("txt", "9");
			result.put("loanerObject", loanerMapper.loadingLoanerInformationById(params));
			return new Message("0000", result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Message loanSaveLoanAgain(Map params) throws KHException {

		/*loan information*/
		Validation.isNumber((String)params.get("total_money"), "total");
		Validation.isNumber((String)params.get("decrement"), "decrement");
		Validation.isNumber((String)params.get("time"), "time");		
		Validation.isRate((String)params.get("rate"), "rate");
		HashMap<String, String> param = new HashMap<>();
		User user = new User();
		Loaner loaner = new Loaner();
		Loan loan = new Loan();
		try {
			
			Long total_money= Long.valueOf((String)params.get("total_money"));
			int  decrement  = Integer.valueOf((String)params.get("decrement"));
			Double rate     = Double.valueOf((String)params.get("rate"));
			int time        = Integer.valueOf((String)params.get("time"));
			int  day        = Integer.valueOf((String)params.get("day"));
			Double prak_derm  = (double) (total_money / time);
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!auth.getPrincipal().equals("anonymousUser")) {
				user = (User) auth.getPrincipal();
				
			}else {
				throw new KHException("9999", "user id");
			}
			
			int loaner_id = Integer.valueOf((String)params.get("loaner_id"));
			param.put("loaner_id", String.valueOf(loaner_id));
			param.put("txt", "9");
			loaner = (Loaner)loanerMapper.loadingLoanerInformationById(param);
			System.out.println(loaner.toString());
			loaner.setTxt("1");
			loaner.setAction("Update Loaner Information (txt)");
			if (loanerMapper.loanerUpdateById(loaner) < 0) {
				throw new KHException("9999", "update fails");
			}
			
			loan.setLoaner_id(loaner_id);
			loan.setTotal_money(total_money);
			loan.setUser_id(user.getUser_id());
			loan.setStart_date((String)params.get("start_date"));
			loan.setCount_date(day);
			loan.setRate(rate);
			loan.setType_payment((String)params.get("type_payment"));
			loan.setType_money("1");
			loan.setTime(time);
			loan.setDecrement(decrement);
			loan.setSts("1");
			loan.setTxt("1");
			loan.setModify_by(user.getUser_id());
			loan.setModify_date(Common.getCurrentDate());
			loan.setAction("inser Loan");
					
			
			loanMapper.insertLoanItem(loan);
			
			Date dt = formatter.parse((String)params.get("start_date"));
			
			
			for(int i=1; i<= Integer.valueOf((String)params.get("time")) ; i++) {
				LoanPayment loanPayment = new LoanPayment();
				dt = DateUtils.addDays(dt, day);
				
				Double total_rate = 0.0;
				if (decrement == 0) {
					total_rate = (Double) ((prak_derm * rate) / 100); 
				}else {
					total_rate = (Double) ((total_money * rate) / 100); 
				}

				Double total_left = total_money - (prak_derm * i) ;
				
				
				loanPayment.setLoan_id(loan.getLoan_id());
				loanPayment.setPayment_date(formatter.format(dt).toString());
				loanPayment.setPrak_derm(prak_derm);
				loanPayment.setTotal_rate(total_rate);
				loanPayment.setTotal_left(total_left);
				loanPayment.setTxt("1");
				loanPayment.setModify_by(user.getUser_id());
				loanPayment.setModify_date(Common.getCurrentDate());
				loanPayment.setAction("Insert Loaner payment");
				loanMapper.insertLoanPayment(loanPayment);
			}

			return new Message("0000","successfull");
		}catch(Exception e) {
			throw new KHException("9999",e.getMessage());
		}
	}
	
	
	public Message loadingLoanview(int loaner_id,String id) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, String> params = new HashMap<>();
		try {
			
			System.out.println(" loaner id =="+ loaner_id);
			params.put("loaner_id", String.valueOf(loaner_id));
			HashMap<String, String> res = loanMapper.loadingLoanView(params);			
			result.put("loanObject", res);
			if (id.equals("loaner")) {
					result.put("loanList", loanMapper.loadingLoanById(params));
			}	
			// this param use for only loadingLoanPayment can not input below
			params.put("loan_id", String.valueOf(res.get("loan_id")));			
			//result.put("listtypePayment", loanMapper.loadingTypePayment());					
			result.put("loanPaymentList", loanMapper.loadingLoanPayment(params));
			return new Message("0000", result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	public Message loanShowBytime(int loan_id) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		HashMap<String, String> params = new HashMap<>();
		try {
			params.put("loan_id", String.valueOf(loan_id));	
			result.put("listtypePayment", loanMapper.loadingTypePayment());
			result.put("loanerObject", loanMapper.loadingLoanById(params));					
			result.put("loanPaymentList", loanMapper.loadingLoanPayment(params));
			return new Message("0000", result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message loadingLoanListView(HashMap<String, String> params) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		try {
			
			PaginationUtils.perPage = Integer.valueOf(params.get("perPage"));
			PaginationUtils.currentPage = Integer.valueOf(params.get("currentPage"));			
			params.put("search", (String)params.get("search").trim());
			params.put("start",  String.valueOf(PaginationUtils.getStart()));
			params.put("perPage", String.valueOf(PaginationUtils.perPage));	
			params.put("startDate", (String)params.get("startDate").trim());
			params.put("endDate",  String.valueOf(params.get("endDate").trim()));
			params.put("typePayment", String.valueOf(params.get("typePayment")));
			System.out.println(" searcher "+ (String)params.get("search").trim());
			PaginationUtils.total = Long.valueOf(loanMapper.loadingLoanListViewCount(params));
			result.put("total_row", PaginationUtils.total);
			result.put("loanList", loanMapper.loadingLoanListView(params));
			result.put("typePaymentList", loanMapper.loadingTypePayment());
			result.put("typePayment", String.valueOf(params.get("typePayment")));
			return new Message("0000",result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message loadingLoanEdit(HashMap<String, String> params) throws KHException {
		HashMap<String, Object> result = new HashMap<>();
		//HashMap<String, String> param = new HashMap<>();
		try {
			//param.put("loaner_id", String.valueOf(params.get("loaner_id")));
			//result.put("loanerObject", loanerMapper.loadingLoanerInformationById(params));
			result.put("loanObject", loanMapper.loadingLoanViewEdit(params));
			result.put("loanPaymentList", loanMapper.loadingLoanPayment(params));
			return new Message("0000", result);
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Message loanSaveUdateItem(HashMap<String, String> params) throws KHException {
		HashMap<String, String> param = new HashMap<>();
		/*Loaner information */
		Validation.isNumber((String)params.get("id_card"), "id card");
		Validation.isNumber((String)params.get("phone"), "phone");
		Validation.isBlank((String)params.get("address"), "address");
		Validation.isBlank((String)params.get("loaner_name"), "loaner_name");

		Validation.isNumber((String)params.get("total_money"), "total");
		Validation.isNumber((String)params.get("decrement"), "decrement");
		Validation.isNumber((String)params.get("time"), "time");		
		Validation.isRate((String)params.get("rate"), "rate");
		User user = new User();
		Loaner loaner = new Loaner();
		Loan loan = new Loan();
		try {
			Long total_money= Long.valueOf((String)params.get("total_money"));
			int  decrement  = Integer.valueOf((String)params.get("decrement"));
			Double rate     = Double.valueOf((String)params.get("rate"));
			int time        = Integer.valueOf((String)params.get("time"));
			int  day        = Integer.valueOf((String)params.get("day"));
			Double prak_derm  = (double) (total_money / time);
			
			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!auth.getPrincipal().equals("anonymousUser")) {
				user = (User) auth.getPrincipal();
				
			}else {
				throw new KHException("9999", "user id");
			}
			
			int loaner_id = Integer.valueOf((String)params.get("loaner_id"));
			param.put("loaner_id", String.valueOf(loaner_id));
			loaner = (Loaner)loanerMapper.loadingLoanerInformationById(param);
			
			if (loaner == null) {
				throw new KHException("9999", "update fails");
			}
			loaner.setLoaner_name((String)params.get("loaner_name"));
			loaner.setGender((String)params.get("gender"));
			loaner.setPhone((String)params.get("phone"));
			loaner.setId_card((String)params.get("id_card"));
			loaner.setAddress((String)params.get("address"));
			loaner.setModify_by(user.getUser_id());
			loaner.setModify_date(Common.getCurrentDate());
			loaner.setAction("Update Loaner Information (txt)");
			if (loanerMapper.loanerUpdateById(loaner) < 0) {
				throw new KHException("9999", "update fails");
			}
			
			int loan_id = Integer.valueOf(params.get("loan_id"));
			System.out.println("  sakjjdflksafd "+loan_id);
			loan        = loanMapper.loadingLoanViewCheck(loan_id);
			if (loan == null) {
				throw new KHException("9999", "updatefails");
			}
			
			loan.setLoaner_id(loaner.getLoaner_id());
			loan.setTotal_money(total_money);
			loan.setUser_id(user.getUser_id());
			loan.setStart_date((String)params.get("start_date"));
			loan.setCount_date(day);
			loan.setRate(rate);
			loan.setType_payment((String)params.get("type_payment"));
			loan.setTime(time);
			loan.setDecrement(decrement);
			loan.setModify_by(user.getUser_id());
			loan.setModify_date(Common.getCurrentDate());
			loan.setAction("update Loan");
			
			if (loanMapper.updateLoanById(loan) < 0) {
				throw new KHException("9999", "laon update fails");
			}
			
            Date dt = formatter.parse((String)params.get("start_date"));
			
            
            if (loanMapper.deleteLoanPaymentByLoanId(loan_id) < 0) {
            	throw new KHException("9999", "laon update fails");
            }
			
			for(int i=1; i<= Integer.valueOf((String)params.get("time")) ; i++) {
				LoanPayment loanPayment = new LoanPayment();
				dt = DateUtils.addDays(dt, day);
				
				Double total_rate = 0.0;
				if (decrement == 0) {
					total_rate = (Double) ((prak_derm * rate) / 100); 
				}else {
					total_rate = (Double) ((total_money * rate) / 100); 
				}

				Double total_left = total_money - (prak_derm * i) ;
				
				
				loanPayment.setLoan_id(loan.getLoan_id());
				loanPayment.setPayment_date(formatter.format(dt).toString());
				loanPayment.setPrak_derm(prak_derm);
				loanPayment.setTotal_rate(total_rate);
				loanPayment.setTotal_left(total_left);
				loanPayment.setTxt("1");
				loanPayment.setModify_by(user.getUser_id());
				loanPayment.setModify_date(Common.getCurrentDate());
				loanPayment.setAction("Insert Loaner payment");
				loanMapper.insertLoanPayment(loanPayment);
			}
			
			return new Message("0000", "sucessfull hz");
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	public Message missingPaymentList(HashMap<String, String> params) throws KHException {
		try {
			return new Message("0000",loanMapper.missingPaymentList(params));
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Message loanPaymentSaveUpdate(HashMap<String,String> params) throws KHException {
		try {
			User user = new User();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (!auth.getPrincipal().equals("anonymousUser")) {
				user = (User) auth.getPrincipal();
				
			}else {
				throw new KHException("9999", "user id");
			}
			for (int i=0;i<params.size();i++) {
				HashMap<String, String> param = new HashMap<>();
				param.put("payment_id", (String)params.get("list["+i+"][payment_id]"));
				param.put("modify_by", String.valueOf(user.getUser_id()));
				param.put("modify_date", Common.getCurrentDate());
				param.put("note", (String)params.get("list["+i+"][note]"));
				param.put("txt", "9");
				param.put("action", "update Information");
				if (loanMapper.loanPaymentSaveUpdate(param)<0) {
					throw new KHException("9999", "fails");
				}
			}
			return new Message("0000","successfull");
		}catch(Exception e) {
			throw new KHException("9999", e.getMessage());
		}
	}
	
}


