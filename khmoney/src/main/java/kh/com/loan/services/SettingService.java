package kh.com.loan.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kh.com.loan.domains.Message;
import kh.com.loan.domains.Setting;
import kh.com.loan.mappers.SettingMapper;
import kh.com.loan.utils.KHException;
import kh.com.loan.utils.Validation;

@Service
public class SettingService {

	@Autowired
	private SettingMapper settingMapper;
	
	public  Message loadingListSetting() throws KHException {
		try {				
			List<Setting> settingList = (List) settingMapper.loadingListSetting();	
			List<Setting> columnList  = (List) settingMapper.loadingListColumns();
			HashMap<String, Object> objSetting = new HashMap<>();
			for( Setting setting : columnList) {
				
				List<Setting> setList = new ArrayList<>();
				
				for(Setting sett : settingList) {
					if (setting.getType() == sett.getType()) {
						setList.add(sett);
					}
				}
				objSetting.put(setting.getColumns(), setList);
			}
			return new Message("0000", objSetting);			
		}catch(Exception e)
		{
			e.printStackTrace();
			throw new KHException("9999", e.getMessage());
		}
	}
	
     public Message settingEditById(Map map) throws KHException {
    	
    	Validation.isBlank((String)map.get("value"),  "សូមធ្វើការបញ្ចូលចំនួនដងដែលត្រូវបង់ប្រាក់!");
    	Validation.isBlank((String)map.get("rate"),   "សូមធ្វើការបញ្ចូលអត្រាការប្រាក់!");
		Validation.isNumber((String)map.get("value"), "អនុញ្ញាតបញ្ចូលតែលេខប៉ុណ្ណោះ");
		Validation.isRate((String)map.get("rate"),    "ការបញ្ជូលការប្រាក់ខុសទំរងសូមផ្ទៀងផ្ទាត់ម្ដងទៀត");
		Validation.isBlank((String)map.get("id"),     "Id is not allow null");
	    
		try {
			
			Setting setting =  settingMapper.loadingSettingById(Integer.valueOf(map.get("id").toString()));
			setting.setValue(Integer.valueOf(map.get("value").toString().replaceAll("\\P{Print}","")));
			setting.setRate(Double.valueOf((String)map.get("rate")));
			
			if (settingMapper.settingEditById(setting) < 0) {
				return new Message("9999", "ការកែប្រែរបស់លោកអ្នកទទួលបរាជ័យ");
			}
			return new Message("0000", "ការកែប្រែរបស់លោកអ្នកទទួលជោគជ័យ");
		}catch( Exception e) {
			e.printStackTrace();
			throw new KHException("9999", e.getMessage());
		}
		//return new Message();
	}
    public Message saveNewCountAndRate(Map map) throws KHException {
    	Validation.isBlank((String)map.get("value"),  "សូមធ្វើការបញ្ចូលចំនួនដងដែលត្រូវបង់ប្រាក់!");
    	Validation.isBlank((String)map.get("rate"),   "សូមធ្វើការបញ្ចូលអត្រាការប្រាក់!");
    	Validation.isNumber((String)map.get("value"), "អនុញ្ញាតបញ្ចូលតែលេខប៉ុណ្ណោះ");
    	Validation.isNumber((String)map.get("type"),  "អនុញ្ញាតបញ្ចូលតែលេខប៉ុណ្ណោះ");
    	Validation.isNumber((String)map.get("day"),   "អនុញ្ញាតបញ្ចូលតែលេខប៉ុណ្ណោះ");
		Validation.isRate((String)map.get("rate"),    "ការបញ្ជូលការប្រាក់ខុសទំរងសូមផ្ទៀងផ្ទាត់ម្ដងទៀត");
       try {
    	   Setting setting = new Setting();
    	   setting.setColumns((String)map.get("columns"));
    	   setting.setValue(Integer.valueOf(map.get("value").toString().replaceAll("\\P{Print}","")));
    	   setting.setRate(Double.valueOf((String)map.get("rate")));  
    	   setting.setDay(Integer.valueOf(map.get("day").toString()));   	
    	   setting.setType(Integer.valueOf(map.get("type").toString()));  
    	   setting.setSts("1");
    	   setting.setTxt("2");
    	   if (settingMapper.saveNewCountAndRate(setting) > 0) {
    		   return new Message("0000", "ការបញ្ជូលទិន្នន័យរបស់លោកអ្នកទទួលជោគជ័យ");
    	   }
    	   return new Message("9999", "ការបញ្ជូលទិន្នន័យរបស់លោកអ្នកទទួលបរាជ័យ");
       }catch(Exception e) {
    	   throw new KHException("9999",e.getMessage());
       }
    }
    
    public Message deleteSettingById(int id) throws KHException {
    	try {
    		Setting setting =  settingMapper.loadingSettingById(id);
    		setting.setSts("9");
    		if (settingMapper.settingEditById(setting) > 0) {
				return new Message("0000", "ការលុបទិន្នន័យទទួលបានជោគជ័យ");
			}
			return new Message("9999", "ការលុបទិន្នន័យទទួលបានបរាជ័យ");
    	}catch(Exception e) {
    		throw new KHException("9999",e.getMessage());
    	}
    }
}
