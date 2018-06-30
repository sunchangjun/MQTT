package framework.service.Impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import framework.common.CollectionUtils;
import framework.dao.CommodityRoadRepository;
import framework.dao.OrderCommodityRoadRepository;
import framework.entity.po.CommodityRoad;
import framework.entity.po.OrderCommodityRoad;
import framework.service.interf.TransactionService;




@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private CommodityRoadRepository commodityRoadRepository;

	@Autowired
	private OrderCommodityRoadRepository orderShippingLaneRepository;

	/**
	 * 保存货道库存和订单货道表
	 * @param commodityRoadList
	 * @param orderShippingLaneList
	 * @return
	 */
	@Transactional
	public boolean shippingOptions(CommodityRoad commodityRoad,OrderCommodityRoad orderShippingLane) {

		CommodityRoad dbReturnCommodityRoad= commodityRoadRepository.save(commodityRoad);
		OrderCommodityRoad dbReturnOrderShippingLane = orderShippingLaneRepository.save(orderShippingLane);
		if (null != dbReturnOrderShippingLane && null != dbReturnCommodityRoad) {
			return true;
		}

		return false;
	}
	/**
	 * 保存货道库存和订单货道表
	 * @param commodityRoadList
	 * @param orderShippingLaneList
	 * @return
	 */
	@Transactional
	public boolean shippingOptionsList(List<CommodityRoad >commodityRoadList,List<OrderCommodityRoad> orderShippingLaneList) {
		
		List<CommodityRoad> dbReturnCommodityRoad= commodityRoadRepository.save(commodityRoadList);
		List<OrderCommodityRoad> dbReturnOrderShippingLane = orderShippingLaneRepository.save(orderShippingLaneList);
		if (null != dbReturnOrderShippingLane && null != dbReturnCommodityRoad) {
			return true;
		}
		
		return false;
	}

}
