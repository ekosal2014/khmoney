package kh.com.loan.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kh.com.loan.domains.Message;
import kh.com.loan.services.SettingService;
import kh.com.loan.utils.KHException;

@Controller
public class SetingController {
	
	@Autowired
	private SettingService settingService;
	
	@RequestMapping(value = "/loadingListSetting",method = RequestMethod.GET)
	public @ResponseBody Message loadingListSetting() throws KHException {
		return settingService.loadingListSetting();
	}
	
	@RequestMapping(value = "/settingEditById", method = RequestMethod.GET)
	public @ResponseBody Message settingEditById(@RequestParam Map map) throws KHException {
		return settingService.settingEditById(map);
	}
	
	@RequestMapping(value = "/saveNewCountAndRate", method = RequestMethod.GET)
	public @ResponseBody Message saveNewCountAndRate(@RequestParam Map map) throws KHException {
		return settingService.saveNewCountAndRate(map);
	}
	
	@RequestMapping(value = "/deleteSettingById", method = RequestMethod.GET)
	public @ResponseBody Message deleteSettingById(@RequestParam("id") int id) throws KHException {
		return settingService.deleteSettingById(id);
	}

}
