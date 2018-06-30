package framework.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 订单货道出货表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_order_shipping_lane")
public class OrderShippingLane implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//订单货道id
	@Id
	@Column(name="order_commodity_road_id")
	private Long orderCommodityRoadId;
	//订单id
	@Column(name="order_id")
	private  Long  orderId;
	
	//货道号
	@Column(name="commodity_road_id")
	private Long commodityRoadId;
	
	//数量
	@Column(name="commodity_num")
	private Integer commodityNum;

	public Long getOrderCommodityRoadId() {
		return orderCommodityRoadId;
	}

	public void setOrderCommodityRoadId(Long orderCommodityRoadId) {
		this.orderCommodityRoadId = orderCommodityRoadId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCommodityRoadId() {
		return commodityRoadId;
	}

	public void setCommodityRoadId(Long commodityRoadId) {
		this.commodityRoadId = commodityRoadId;
	}

	public Integer getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	
	
}
