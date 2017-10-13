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
    	
		Validation.isNumber((String)map.get("value"), "number is not allow String");
		Validation.isRate((String)map.get("rate"), "Rate is wrong formate!");
		Validation.isBlank((String)map.get("id"), "Id is not allow null");
	    
		try {
			
			Setting setting =  settingMapper.loadingSettingById(Integer.valueOf(map.get("id").toString()));
			setting.setValue(Integer.valueOf(map.get("value").toString().replaceAll("\\P{Print}","")));
			setting.setRate(Double.valueOf((String)map.get("rate")));
			
			if (settingMapper.settingEditById(setting) > 0) {
				return new Message("0000", "Edit is succefull");
			}
			return new Message("9999", "Edit is fails");
		}catch( Exception e) {
			e.printStackTrace();
			throw new KHException("9999", e.getMessage());
		}
		//return new Message();
	}
    public Message saveNewCountAndRate(Map map) throws KHException {
    	Validation.isNumber((String)map.get("value"), "number is not allow String");
    	Validation.isNumber((String)map.get("type"),  "type is not allow String");
    	Validation.isNumber((String)map.get("day"),   "number is not allow String");
		Validation.isRate((String)map.get("rate"),    "Rate is wrong formate!");
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
    		   return new Message("0000", "Insert successfull");
    	   }
    	   return new Message("0000", "Insert fails");
       }catch(Exception e) {
    	   throw new KHException("9999","Some Wrong");
       }
    }
    
    public Message deleteSettingById(int id) throws KHException {
    	try {
    		Setting setting =  settingMapper.loadingSettingById(id);
    		setting.setSts("9");
    		if (settingMapper.settingEditById(setting) > 0) {
				return new Message("0000", "Edit is succefull");
			}
			return new Message("9999", "Edit is fails");
    	}catch(Exception e) {
    		throw new KHException("9999",e.getMessage());
    	}
    }
}
