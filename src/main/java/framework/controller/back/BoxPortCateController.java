package framework.controller.back;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import framework.common.CollectionUtils;
import framework.common.JSONExt;
import framework.common.JSONText;
import framework.common.result.Result;
import framework.dao.BoxPortCateRepository;
import framework.entity.po.BoxPortCate;

@RestController
@RequestMapping("/back/boxPortCate")
public class BoxPortCateController {
	
	@Autowired
	BoxPortCateRepository boxPortCateRepository;
	
	@PostMapping("/getAllBoxPortCate")
	public  Result getAllBoxPortCate() {	
		JSONArray ja=new  JSONArray();
		List<BoxPortCate> boxPortCate=boxPortCateRepository.findAll();
		if(CollectionUtils.isNotEmpty(boxPortCate)) {
			for (BoxPortCate boxPortCate2 : boxPortCate) {
			JSONObject jo=JSONText.JavaBeanToJsonObject(boxPortCate2);
				ja.add(jo);
			}
		}	
		return  Result.setData(true, ja);
	}

}
