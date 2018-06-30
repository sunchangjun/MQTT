package framework.mqtt.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSON;
import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;

import framework.dao.AdvRepository;
import framework.dao.ImageResourceRepository;
import framework.entity.po.Adv;
import framework.mqtt.MQttUtil;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.DataType;
import framework.pojo.Request;

@Component
public class AdvHandler implements IMqttHandler {
	@Autowired
	ImageResourceRepository imageResourceRepository;
	@Autowired
	AdvRepository advRepository;

	@Autowired
	MQttUtil mQttUtil;

	@Override
	public void exec(Request bean) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Adv> list = advRepository.findAll();
		for (int i = 0; i < list.size(); i++) {
			Adv adv=list.get(i);
			adv.setImgUrl(adv.getImageResource().getUrl());
		}
		map.put("advs", list);
		Request bind = new Request();
		bind.setDataType(DataType.ADV);
		bind.setDevice(bean.getDevice());
		bind.setData(Request.Object2Bytes(map));
		mQttUtil.sendP2P(bind);
	}
}
