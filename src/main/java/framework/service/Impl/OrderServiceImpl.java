package framework.service.Impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import framework.dao.CommodityRepository;
import framework.dao.CommodityRoadRepository;
import framework.dao.OrderCommodityRepository;
import framework.dao.OrderCommodityRoadRepository;
import framework.dao.OrderRepository;
import framework.dao.OrderStateRcdRepository;
import framework.dao.OrderStateRepository;
import framework.entity.po.Box;
import framework.entity.po.Commodity;
import framework.entity.po.CommodityRoad;
import framework.entity.po.Order;
import framework.entity.po.OrderCommodity;
import framework.entity.po.OrderCommodityRoad;
import framework.entity.po.OrderState;
import framework.entity.po.OrderStateRcd;
import framework.enums.IsDeleteEnum;
import framework.service.interf.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private CommodityRepository commodityRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private CommodityRoadRepository commodityRoadRepository;
	
	@Autowired
	private OrderStateRepository orderStateRepository;
	
	@Autowired
	private OrderCommodityRoadRepository orderCommodityRoadRepository;
	@Autowired
	private OrderCommodityRepository orderCommodityRepository;
	
	@Autowired
	private	OrderStateRcdRepository orderStateRcdRepository;

	@Override
	@Transactional
	public Order createOrder(Map<String, Map<Long, Integer>> commodityList, Box box) {
		Order order = new Order();
		order.setBox(box);
		int sumNum=0;
		Double orderMoney=0d;
		Double bi_money=0d;
		Double dian_bi=0d;
		order.setCreateTime(System.currentTimeMillis());
		List<OrderCommodity> orderCommoditys = new ArrayList<OrderCommodity>();
		
		Iterator<String> cit = commodityList.keySet().iterator();
		for (Iterator<String> it = cit; it.hasNext();) {
			String _id = it.next();
			String[] ids=_id.split("_");
			Map<Long, Integer> mp = commodityList.get(_id);
			
			Long commodity_id=Long.valueOf(ids[0]);
			//Long boxPortCateId=Long.valueOf(ids[1]);
			
			Commodity cty = commodityRepository.findOne(commodity_id);
			OrderCommodity orcty=new OrderCommodity();
			orcty.setBox(box);
			orcty.setOrder(order);
			orcty.setStocks(cty.getStocks());
			orcty.setBiPrice(cty.getBiPrice());
			orcty.setDianBi(cty.getDianBi());
			orcty.setSalePrice(cty.getPrice());
			orcty.setCtime(System.currentTimeMillis());
			orcty.setStare((short)1);
			orcty.setGiveFen(cty.getGiveFen());
			orcty.setCostPrice(cty.getCostPrice());
			orcty.setCommodity(cty);
			int number=0;
			Iterator<Long> its = mp.keySet().iterator();
			List<OrderCommodityRoad> orderCommodityRoads = new ArrayList<OrderCommodityRoad>();
			for (Iterator<Long> itx = its; itx.hasNext();) {
				Long commodityRoadId = itx.next();
				CommodityRoad one = commodityRoadRepository.findOne(commodityRoadId);
				OrderCommodityRoad orderCommodityRoad = new OrderCommodityRoad();
				orderCommodityRoad.setBox(box);
				orderCommodityRoad.setCommodityRoad(one);
				orderCommodityRoad.setOrderCommodity(orcty);
				orderCommodityRoad.setCtime(System.currentTimeMillis());
				orderCommodityRoad.setIsDelete(1);
				orderCommodityRoad.setCommodityNum(mp.get(commodityRoadId));
				number+=mp.get(commodityRoadId);
				sumNum+=mp.get(commodityRoadId);
				orderMoney+=cty.getPrice()*mp.get(commodityRoadId);
				bi_money+=cty.getBiPrice()*mp.get(commodityRoadId);
				dian_bi+=cty.getDianBi()*mp.get(commodityRoadId);
				
				orderCommodityRepository.save(orcty);
				orderCommodityRoadRepository.save(orderCommodityRoad);
				one.setCommodityNum(one.getCommodityNum()-mp.get(commodityRoadId));
				commodityRoadRepository.save(one);
				try {
					cty.setStocks(cty.getStocks()-mp.get(commodityRoadId));
					commodityRepository.save(cty);
				} catch (Exception e) {
				}
				orderCommodityRoads.add(orderCommodityRoad);
			}	
			orcty.setCommodityNum(number);
			orcty.setOrderCommodityRoads(orderCommodityRoads);
			orderCommoditys.add(orcty);
		}
        OrderState orderState = orderStateRepository.findOne(1L);
        
        
        
        List<OrderStateRcd> rcds=new ArrayList<OrderStateRcd>();
		OrderStateRcd orderStateRcd =new OrderStateRcd();
		orderStateRcd.setOrderState(orderState);
		orderStateRcd.setRcdTime(System.currentTimeMillis());
		orderStateRcd.setOrder(order);
		orderStateRcd.setRemarks("createOrder");
		orderStateRcdRepository.save(orderStateRcd); 
		rcds.add(orderStateRcd);
		
		order.setOrderState(orderState);
		order.setOrderStateRcds(rcds);
		order.setOrderCommoditys(orderCommoditys);
		order.setCommodityNum(sumNum);
		order.setOrderMoney(orderMoney);
		order.setBiMoney(bi_money);
		order.setDianBi(dian_bi);
		orderRepository.save(order);
		return order;
	}

}
