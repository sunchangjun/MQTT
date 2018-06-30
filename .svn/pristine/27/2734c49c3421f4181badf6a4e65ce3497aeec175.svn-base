package framework.mqtt.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import framework.dao.CommodityRoadRepository;
import framework.entity.po.CommodityRoad;
import framework.mqtt.service.CommodityRoadService;

@Service
public class MqttCommodityRoadServiceImpl implements CommodityRoadService {
	
	@Autowired
	CommodityRoadRepository commodityRoadRepository;
	/**
	 * 同步货道数据
	 * @return
	 */
	public boolean synchronizeCommodityRoad(String s) {
		//解析数据
		JSONObject.parseArray(s, String.class);
		List<CommodityRoad> CommodityRoadList=new  ArrayList<CommodityRoad>();
		for (CommodityRoad commodityRoad : CommodityRoadList) {
			
		}
		commodityRoadRepository.save(CommodityRoadList);
		return false;
	}

}
