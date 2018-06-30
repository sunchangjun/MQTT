package framework.mqtt.handler;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import framework.common.okhttp3.OkHttp3Util;
import framework.controller.box.MyKeyCreater;
import framework.dao.OrderCommodityRoadRepository;
import framework.dao.OrderRepository;
import framework.dao.OrderStateRcdRepository;
import framework.dao.OrderStateRepository;
import framework.entity.po.Commodity;
import framework.entity.po.Order;
import framework.entity.po.OrderCommodityRoad;
import framework.entity.po.OrderStateRcd;
import framework.enums.OrderStatusEnum;
import framework.mqtt.util.IMqttHandler;
import framework.pojo.Request;
import framework.service.interf.OrderStateRcdService;
/** 
 * 货道出货反馈通知
 * @author Administrator
 *
 */
@Component("commodityRoadFeedbackHandler")
public class commodityRoadFeedbackHandler implements IMqttHandler {
	
	
	

	
	@Autowired
	private OrderStateRcdService orderStateRcdService;
	
	@Autowired
	private OrderStateRcdRepository orderStateRcdRepository;

	@Autowired
	private OrderStateRepository orderStateRepository;
	private static final Logger logger = LoggerFactory.getLogger(commodityRoadFeedbackHandler.class);
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderCommodityRoadRepository orderCommodityRoadRepository;
	@Override
	public void exec(Request bean) {
		//{"_successNum":1,"_all":2,"_list":[{"number":2,"successNum":1,"orderId":103,"failNum":1,"devicePort":"/dev/ttyS1","code":"11","orderCommodityRoadId":130}],"_failNum":1}-----------------------
		String response = new String(bean.getData());
		logger.info(response);
		JSONObject jparam = JSON.parseObject(response);

		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		long orderId = 0;
		JSONArray ja_oc = jparam.getJSONArray("_list");
		if(null != ja_oc) {
			int size = ja_oc.size();
			for(int i = 0; i < size; i++) {
				JSONObject jo_oc = ja_oc.getJSONObject(i);
				int number = jo_oc.getIntValue("number");
				int successNum = jo_oc.getIntValue("successNum");
				int failNum = jo_oc.getIntValue("failNum");
				long orderCommodityRoadId = jo_oc.getLongValue("orderCommodityRoadId");
				OrderCommodityRoad ocr = orderCommodityRoadRepository.findOne(orderCommodityRoadId);
				if(null == ocr) {
					continue;
				}
				orderId = ocr.getOrderCommodity().getOrder().getOrderId();
				Commodity commodity = ocr.getOrderCommodity().getCommodity();
				long commodityId = commodity.getCommodityId();
				JSONObject jobj = new JSONObject();
				jobj.put("commodityId", commodityId);
				jobj.put("failCommodityNum", failNum);
				jobj.put("successNum", successNum);
				ja.add(jobj);
			}
		}
		jo.put("orderId", orderId);
		jo.put("orderCommoditys", ja);
		List<String> values = new ArrayList<String>();
		values.add(jo.toJSONString());
		String key1 = MyKeyCreater.createKey(values);
		StringBuffer sb=new StringBuffer();
		sb.append("http://api.tiand99.com/box/box_order_ship_notify.action?datas=" +jo.toJSONString()+"&$key="+key1);
		OkHttp3Util.get(sb.toString());
		
		Order dbOrder = orderRepository.findOne(orderId);
		OrderStateRcd orderStateRcd = new OrderStateRcd();
		orderStateRcd.setOrderStateId(OrderStatusEnum.WAITING_FOR_SHIPMENT.getType());
		orderStateRcd.setOrder(dbOrder);
		orderStateRcd.setOrderState(orderStateRepository.findOne(3L));
		orderStateRcd.setRcdTime(System.currentTimeMillis());
		orderStateRcd.setRemarks("dropOutFinish");
		orderStateRcdService.saveOrderStateRcd(orderStateRcd); // 保存
		dbOrder.setOrderState(orderStateRepository.findOne(2L));
		dbOrder.setFinishTime(System.currentTimeMillis());
		orderRepository.save(dbOrder);
		
	}
	
	
	public static void main(String[] args) {
		List<String> values = new ArrayList<String>();
		values.add("231");
		String key1 = MyKeyCreater.createKey(values);
		StringBuffer sb=new StringBuffer();
		sb.append("http://box.tiand99.net:55/transaction/orderInfo?orderId=231&$key="+key1);
		System.out.println(OkHttp3Util.get(sb.toString()));
	}
}
