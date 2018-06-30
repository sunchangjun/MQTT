package framework.mqtt.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import framework.dao.BoxPortCateRepository;
import framework.dao.BoxPortRepository;
import framework.entity.po.BoxPort;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.Request;

@Component
public class ChangeBoxPortCateHandler implements IMqttHandler {

	@Autowired
	BoxPortRepository boxPortRepository;
	@Autowired
	BoxPortCateRepository boxPortCateRepository;

	@SuppressWarnings("unchecked")
	@Override
	public void exec(Request bean) {
		String response = new String(bean.getData());
		Map<String, Object> mp = JSON.parseObject(response, Map.class);
		long boxPortId = Long.valueOf(String.valueOf(mp.get("boxPortId")));
		long boxPortCateId = Long.valueOf(String.valueOf(mp.get("boxPortCateId")));
		BoxPort boxport = boxPortRepository.findOne(boxPortId);
		boxport.setBoxPortCate(boxPortCateRepository.findOne(boxPortCateId));
		boxPortRepository.save(boxport);
	}
}
