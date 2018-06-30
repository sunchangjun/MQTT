package framework.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单货道出货表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_order_commodity_road")
public class OrderCommodityRoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4129225324022048789L;

	// 订单货道id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "order_commodity_road_id")
	private Long orderCommodityRoadId;

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_id")
	private Box box;


	// 数量
	@Column(name = "number")
	private Integer commodityNum;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_road_id")
	private CommodityRoad commodityRoad;

	// 创建时间
	@Column(name = "ctime")
	private Long ctime;

	// 删除状态
	@Column(name = "is_delete")
	private Integer isDelete;

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_commodity_id")
	private OrderCommodity orderCommodity;



	// 更新时间
	@Column(name = "utime")
	private Long utime;
	public Long getOrderCommodityRoadId() {
		return orderCommodityRoadId;
	}
	public void setOrderCommodityRoadId(Long orderCommodityRoadId) {
		this.orderCommodityRoadId = orderCommodityRoadId;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	public Integer getCommodityNum() {
		return commodityNum;
	}
	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}
	public CommodityRoad getCommodityRoad() {
		return commodityRoad;
	}
	public void setCommodityRoad(CommodityRoad commodityRoad) {
		this.commodityRoad = commodityRoad;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public OrderCommodity getOrderCommodity() {
		return orderCommodity;
	}
	public void setOrderCommodity(OrderCommodity orderCommodity) {
		this.orderCommodity = orderCommodity;
	}
	public Long getUtime() {
		return utime;
	}
	public void setUtime(Long utime) {
		this.utime = utime;
	}


}
