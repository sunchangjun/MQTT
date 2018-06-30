package framework.service.interf;

import framework.entity.po.Commodity;

public interface CommodityService {
	/**
	 * 保存商品信息
	 * @param commodity
	 * @return
	 */
	Commodity saveCommodity(Commodity commodity);
}
