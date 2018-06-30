package framework.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import framework.common.jpa.BaseRepository;
import framework.entity.po.OrderStateRcd;


/**
 * 订单状态表
 * @author Administrator
 *
 */
public interface OrderStateRcdRepository extends BaseRepository<OrderStateRcd,Long>{
	

	
}
