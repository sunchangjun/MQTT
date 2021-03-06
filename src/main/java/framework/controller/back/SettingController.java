package framework.controller.back;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import framework.common.jpa.Query;
import framework.common.jpa.Select;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.SettingRepository;
import framework.entity.po.Setting;
import framework.service.interf.SettingService;

@Controller
@RequestMapping("/back/setting")
public class SettingController {
	
	private static Logger log = LoggerFactory.getLogger(SettingController.class);
	
	@Autowired
	SettingRepository settingRepository;
	
	@Autowired
	SettingService settingService;
	
	@RequestMapping(value = "/addOrUpdateSetting", method = RequestMethod.POST)
	@ResponseBody
	public Result addOrUpdateSetting(Setting setting){
		//参数判断
		boolean bool= null == setting || StringUtils.isBlank(setting.getTitle())  ||  StringUtils.isBlank(setting.getCode());
		if(bool){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		//
		Setting dbReturnSetting=null;
		try {
			dbReturnSetting=	settingService.addOrUpdateSetting(setting);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, dbReturnSetting);
	}

	@RequestMapping(value = "/findSettings", method = RequestMethod.POST)
	@ResponseBody
	public Result findSettings(Integer page,Integer size,Integer isDelete){
		//参数判断
		if(null == page || page<0 || null == size || size<0){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		//查询
		Page<Setting>  pages=null;
		try {
			Pageable paable=new PageRequest(page-1, size);
			Query<Setting> query=new  Query<Setting>();
			
			if(null !=isDelete){
				query.add(Select.eq("is_delete", isDelete));
			}
			pages=	settingRepository.findAll(query,paable);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		} 
		
		
		return Result.setData(true, pages);
	}

}
