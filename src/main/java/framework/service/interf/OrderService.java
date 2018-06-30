package framework.service.interf;

import java.util.Map;

import framework.entity.po.Box;
import framework.entity.po.Order;

public interface OrderService {
	/**
	 * 创建订单
	 * @param commodityList
	 * @param commodity_num
	 * @return
	 */
	Order createOrder(Map<String, Map<Long, Integer>> commodityList, Box box) ;

}
