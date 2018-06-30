package framework.controller.back;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import framework.common.result.Code;
import framework.common.result.Message;
import framework.common.result.Result;
import framework.dao.OrderRepository;
import framework.entity.po.Order;



@Controller
@RequestMapping("/back/order")
public class OrderController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 第三方调用接口(根据订单id,查询某个订单详情)
	 * @param order_id
	 * @return
	 */
	@RequestMapping(value = "/findOrderById")
	@ResponseBody
	public Result findOrderById(Long order_id){	
		//参数判断
		if(null == order_id  || order_id<0){
			return Result.setData(false, Code.getMessage(Code.CNUM003));
		}
		//查询订单
		Order order=	null;
		try {
			 order=	orderRepository.findOne(order_id);
		} catch (Exception e) {
			return Result.setData(false, Code.getMessage(Code.CNUM001));
		}
		return Result.setData(true, order);
	}

}
