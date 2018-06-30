package framework.mqtt.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import framework.mqtt.service.CommodityRoadService;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.Request;
/**
 * 货道数据同步
 * @author Administrator
 *
 */
@Component()
public class commodityRoadHandler implements IMqttHandler{
	@Autowired
	CommodityRoadService commodityRoadService;

	@Override
	public void exec(Request bean) {
		// TODO 需要通知结构和类容
		System.out.println(JSON.toJSON(bean));
		System.out.println(new String(bean.getData()));
		String  response=new   String(bean.getData());
		System.out.println(response);
		boolean bool=commodityRoadService.synchronizeCommodityRoad(response);
	}

}
