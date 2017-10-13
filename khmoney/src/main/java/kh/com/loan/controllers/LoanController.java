package kh.com.loan.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kh.com.loan.domains.Message;
import kh.com.loan.mappers.LoanerMapper;
import kh.com.loan.services.LoanerService;
import kh.com.loan.utils.KHException;

@Controller
public class LoanController {
	
	@Autowired
	private LoanerService loanerService;
	
	@RequestMapping(value ="/loan/loan-again" , method = RequestMethod.GET)
	public String loanAgain(@RequestParam("loaner_id") int loaner_id, ModelMap map) {
		map.addAttribute("loaner_id", loaner_id);
		return "loan-again";
	}
	
	@RequestMapping(value ="/loan/loaner-view-detail" , method = RequestMethod.GET)
	public String loanerViewDetails(@RequestParam("loaner_id") int loaner_id,@RequestParam("id") String id, ModelMap map) {
		map.addAttribute("loaner_id", loaner_id);
		map.addAttribute("id", id);
		map.addAttribute("loan_id", "");
		return "loan-view";
	}
	@RequestMapping(value ="/loan/loan-view-detail" , method = RequestMethod.GET)
	public String loanViewDetails(@RequestParam("loaner_id") int loaner_id,@RequestParam("id") String id,@RequestParam("loan_id") int loan_id, ModelMap map) {
		map.addAttribute("loaner_id", loaner_id);
		map.addAttribute("id", id);
		map.addAttribute("loan_id", loan_id);
		return "loan-view";
	}
	
	@RequestMapping(value ="/loan/loan-edit" , method = RequestMethod.GET)
	public String loanViewEdit(@RequestParam("loaner_id") int loaner_id,@RequestParam("loan_id") int loan_id, ModelMap map) {
		map.addAttribute("loaner_id", loaner_id);
		map.addAttribute("loan_id", loan_id);
		return "loan-edit";
	}
	
	@RequestMapping(value ="/missing-payment/payment", method = RequestMethod.GET)
	public String loanPayment(@RequestParam("loaner_id") int loaner_id,@RequestParam("loan_id") int loan_id,ModelMap map) {
		map.addAttribute("loaner_id", loaner_id);
		map.addAttribute("loan_id", loan_id);
		return "payment";
	}
	
	@RequestMapping(value = "/loanerGetMaxId", method = RequestMethod.GET)
	public @ResponseBody Message loanerGetMaxId() throws KHException {
		return loanerService.loanerGetMaxId();
	}
	
	@RequestMapping(value = "/loadingSettingData", method = RequestMethod.GET)
	public @ResponseBody Message loadingSettingData() throws KHException {
		return loanerService.loadingSettingData();
	}
	
	@RequestMapping(value ="/loadingSettingValue", method = RequestMethod.GET)
	public @ResponseBody Message loadingSettingValue() throws KHException {
		return loanerService.loadingSettingValue();
	}
	
	@RequestMapping(value = "/decrementTypeValue", method = RequestMethod.GET)
	public @ResponseBody Message decrementTypeValue() throws KHException {
		return loanerService.decrementTypeValue();
	}
	
	@RequestMapping(value = "/loanSaveNewItem", method = RequestMethod.GET)
	public @ResponseBody Message loanSaveNewItem(@RequestParam Map params) throws KHException {
		System.out.println(" Map === "+ params);
		return loanerService.loanSaveNewItem(params);
	}
	
	@RequestMapping(value = "/loadingLoanerListInformation", method = RequestMethod.GET)
	public @ResponseBody Message loadingLoanerListInformation(@RequestParam HashMap<String, String> param) throws KHException {		
		return loanerService.loadingLoanerListInformation(param);
	}
	
	@RequestMapping(value = "/loadingLoanAgain", method = RequestMethod.GET)
	public @ResponseBody Message loadingLoanAgain(@RequestParam("loaner_id") int loaner_id) throws KHException {
		return loanerService.loadingLoanAgain(loaner_id);
	}

	@RequestMapping(value = "/loanSaveLoanAgain", method = RequestMethod.GET)
	public @ResponseBody Message loanSaveLoanAgain(@RequestParam Map params) throws KHException {
		System.out.println(" Map === "+ params);
		return loanerService.loanSaveLoanAgain(params);
	}
	
	@RequestMapping(value = "/loadingLoanview", method = RequestMethod.GET)
	public @ResponseBody Message loadingLoanview(@RequestParam("loaner_id") int loaner_id,@RequestParam("id") String id) throws KHException {
		return loanerService.loadingLoanview(loaner_id,id);
	}
	
	@RequestMapping(value = "/loanShowBytime", method = RequestMethod.GET)
	public @ResponseBody Message loanShowBytime(@RequestParam("loan_id") int loan_id) throws KHException {
		return loanerService.loanShowBytime(loan_id);
	}
	
	@RequestMapping(value = "/loadingLoanListView", method = RequestMethod.GET)
	public @ResponseBody Message loadingLoanListView(@RequestParam HashMap<String, String> params) throws KHException {
		return loanerService.loadingLoanListView(params);
	}
	
	@RequestMapping(value = "/loadingLoanEdit", method = RequestMethod.GET)
	public @ResponseBody Message loadingLoanEdit(@RequestParam HashMap<String, String> params) throws KHException {
		System.out.println(" Map === "+ params);
		return loanerService.loadingLoanEdit(params);
	}
	
	@RequestMapping(value = "/loanSaveUdateItem", method = RequestMethod.GET)
	public @ResponseBody Message loanSaveUdateItem(@RequestParam HashMap<String, String> params) throws KHException {
		return loanerService.loanSaveUdateItem(params);
	}
	
	@RequestMapping(value = "/missingPaymentList", method = RequestMethod.GET)
	public @ResponseBody Message missingPaymentList(@RequestParam HashMap<String, String> params) throws KHException {
		return loanerService.missingPaymentList(params);
	}
	
	@RequestMapping(value = "/loanPaymentSaveUpdate", method = RequestMethod.GET)
	public @ResponseBody Message loanPaymentSaveUpdate(@RequestParam HashMap<String,String> params) throws KHException {
		return loanerService.loanPaymentSaveUpdate(params);
	}
	
}
