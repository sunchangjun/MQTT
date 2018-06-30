package framework.mqtt.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;

import framework.dao.BoxPortRepository;
import framework.dao.BoxRepository;
import framework.dao.CommodityBoxPortCateRelRepository;
import framework.dao.CommodityRepository;
import framework.dao.CommodityRoadRepository;
import framework.dao.ImageResourceRepository;
import framework.entity.po.Commodity;
import framework.entity.po.CommodityBoxPortCateRel;
import framework.mqtt.MQttUtil;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.DataType;
import framework.pojo.Request;

@Component
public class CommodityHandler implements IMqttHandler {
	private static final Logger logger = LoggerFactory.getLogger(IMqttHandler.class);

	@Autowired
	CommodityRepository commodityRepository;

	@Autowired
	ImageResourceRepository imageResourceRepository;

	@Autowired
	MQttUtil mQttUtil;

	@Autowired
	CommodityRoadRepository commodityRoadRepository;
	@Autowired
	CommodityBoxPortCateRelRepository commodityBoxPortCateRelRepository;
	@Autowired
	BoxRepository boxRepository;

	@Autowired
	BoxPortRepository boxPortRepository;

	@Override
	public void exec(Request bean) {
		String device = bean.getDevice();
		List<Commodity> list = commodityRepository.findAllByDevice(device);
//		List<ImageResource> imageList = imageResourceRepository.findAllByDevice(device);
		Map<String, Object> map = new HashMap<String, Object>();
		List<CommodityBoxPortCateRel> commodityBoxPortCateRels=commodityBoxPortCateRelRepository.findAllByBoxCode(device);
		map.put("commodityBoxPortCateRels", commodityBoxPortCateRels);
		for (int i = 0; i < list.size(); i++) {
			Commodity commodity=list.get(i);
			commodity.setImgUrl(commodity.getImageResource().getUrl());
		}
		map.put("commodity", list);
//		map.put("commodityImag", imageList);
		// 发送商品数据
		Request bind = new Request();
		bind.setDataType(DataType.COMMODITY);
		bind.setDevice(device);
		bind.setData(Request.Object2Bytes(map));
		mQttUtil.sendP2P(bind);
	}

}
