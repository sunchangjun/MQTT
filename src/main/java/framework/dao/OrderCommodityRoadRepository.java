package framework.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.OrderCommodityRoad;

/**
 * 订单货道表
 * @author Administrator
 *
 */
public interface OrderCommodityRoadRepository extends BaseRepository<OrderCommodityRoad,Long>{
	

}
