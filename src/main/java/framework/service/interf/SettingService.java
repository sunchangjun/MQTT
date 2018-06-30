package framework.service.interf;

import framework.entity.po.Setting;

public interface SettingService {
	/**
	 * 添加或保存系统设置
	 * @param setting
	 * @return
	 */
	Setting addOrUpdateSetting( Setting setting);
}
