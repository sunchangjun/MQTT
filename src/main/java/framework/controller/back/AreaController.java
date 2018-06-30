package framework.controller.back;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import framework.common.JSONText;
import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.AreaRepository;
import framework.entity.po.Area;
import framework.service.interf.AreaService;

@RestController
@RequestMapping("/back/area")
public class AreaController {
	@Autowired
	AreaRepository areaRepository;
	
	@Autowired
	AreaService areaService;
	
	
	@PostMapping("/addOrUpdate")
	public  Result addOrUpdate(Area area){
		if(null  == area || null ==area.getDepth() || StringUtils.isBlank(area.getAreaName())){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONObject jo=null;
		try {
			Area dbArea=areaService.saveArea(area);
			if(null != dbArea){
				jo=JSONText.JavaBeanToJsonObject(dbArea);
				
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
			
		return  Result.setData(true, jo);
	}
	
	
	@PostMapping("/getAll")
	public Result  getAll(){
		JSONArray ja=new  JSONArray();
		try {
			List<Area> list=areaRepository.findAll();
			if(CollectionUtils.isNotEmpty(list)){
				for (Area area : list) {
					ja.add(JSONText.JavaBeanToJsonObject(area));
				}}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
	

		return Result.setData(true, ja);
	}
	
	
	@PostMapping("/deleteArea")
	public Result deleteArea(Long  areaId){
		if(null  == areaId){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		boolean bool=false;
		try {
			areaRepository.delete(areaId);
			bool=true;
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}		
		return  Result.setData(true, bool);
	}
	
	@PostMapping("/getOne")
	public  Result getOne(Long  areaId){
		if(null ==areaId ){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		JSONObject jo=null;
		try {
			Area area=areaRepository.findOne(areaId);
			if(null != area){
				jo=JSONText.JavaBeanToJsonObject(area);
			}
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		
		
		return  Result.setData(true, jo);
	}
	
	

}
