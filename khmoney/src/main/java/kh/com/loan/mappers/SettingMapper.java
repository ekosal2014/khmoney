package kh.com.loan.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kh.com.loan.domains.Setting;

public interface SettingMapper {
	
	public List<Setting> loadingListSetting();
	public List<Setting> loadingListColumns();
	public Setting loadingSettingById(@Param("id") int id);
	public int settingEditById(Setting setting);
	public int saveNewCountAndRate(Setting setting);

}
