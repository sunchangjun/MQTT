package framework.mqtt.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import framework.dao.CommodityRepository;
import framework.dao.CommodityRoadRepository;
import framework.entity.po.CommodityRoad;
import framework.mqtt.service.BindService;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.Request;

@Component
public class SaveCommodityNumChangedHandler implements IMqttHandler {
	@Autowired
	private BindService bindService;
	
	@Autowired
	CommodityRoadRepository commodityRoadRepository;
	
	
	@Autowired
	CommodityRepository commodityRepository;
	
	@Override
	public void exec(Request bean) {
		String rep = new String(bean.getData());
		JSONObject jparam = JSON.parseObject(rep);
		 Long boxId=jparam.getLong("boxId");
         Long boxPortId=jparam.getLong("boxPortId");
         JSONArray ret = jparam.getJSONArray("ret");
         for (int i = 0; i < ret.size(); i++) {
             JSONObject commodity = ret.getJSONObject(i);
             Long commodityRoadId=commodity.getLong("commodityRoadId");
             Long commodityId=commodity.getLong("commodityId");
             Long _commodityId=commodity.getLong("_commodityId");
             Long addNum=commodity.getLong("addNum");
             Long delNum=commodity.getLong("delNum");
             Integer commodityNum=commodity.getInteger("commodityNum");
             Long _commodityNum=commodity.getLong("_commodityNum");
             CommodityRoad road = commodityRoadRepository.findOne(commodityRoadId);
             if(_commodityId!=commodityId) {
            	 road.setCommodity(commodityRepository.findOne(commodityId));
             }
             road.setCommodityNum(commodityNum);
             commodityRoadRepository.save(road);
         }
		System.out.println(boxId);
		System.out.println(boxPortId);
		System.out.println(ret);
		bindService.SynchronizeBox(bean.getDevice());
	}
}
