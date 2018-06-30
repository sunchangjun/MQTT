package framework.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import framework.dao.BoxRepository;
import framework.dao.OrderRepository;
import framework.entity.po.Box;
import framework.entity.po.Order;

@Component
public class GetBoxCodeUtil {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	BoxRepository boxRepository;
	
	/**
	 * 商品id获取柜机标号
	 * @param orderId
	 * @return
	 */
	public  String getBoxCode(Long orderId) {
		Order order=orderRepository.findOne(orderId);
		if(null != order) {		
		Box box=	order.getBox();
		if(null != box) {
			
			return box.getBoxCode();
		}
		}
		return "";
	}
	

}
