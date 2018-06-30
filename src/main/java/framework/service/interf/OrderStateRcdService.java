package framework.service.interf;

import framework.entity.po.OrderStateRcd;

public interface OrderStateRcdService {
	/**
	 * 保存订单状态表
	 * @param rderStateRcd
	 * @return
	 */
	boolean saveOrderStateRcd(OrderStateRcd rderStateRcd);
}
