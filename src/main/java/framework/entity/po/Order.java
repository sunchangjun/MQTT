package framework.entity.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 订单表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_order")
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 订单编号
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "order_id")
	private Long orderId;

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_id")
	private Box box;

	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderStateRcd> orderStateRcds = new ArrayList<OrderStateRcd>();
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_state_id")
	private OrderState orderState;
	
	
	public OrderState getOrderState() {
		return this.orderState;
	}

	public void setOrderState(OrderState orderState) {
		this.orderState = orderState;
	}

	// 商品数量
	@Column(name = "commodity_num")
	private Integer commodityNum;

	// 订单金额
	@Column(name = "order_money")
	private Double orderMoney;
	
	
	@Column(name = "dian_bi")
	private Double	dianBi;
	
	@Column(name = "bi_money")
	private Double	 biMoney;


	public Double getDianBi() {
		return dianBi;
	}

	public void setDianBi(Double dianBi) {
		this.dianBi = dianBi;
	}

	public Double getBiMoney() {
		return biMoney;
	}

	public void setBiMoney(Double biMoney) {
		this.biMoney = biMoney;
	}

	// 支付时间
	@Column(name = "pay_time")
	private Long payTime;

	// 创建时间
	@Column(name = "create_time")
	private Long createTime;

	// 修改时间
	@Column(name = "finish_time")
	private Long finishTime;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "order")
	private List<OrderCommodity> orderCommoditys = new ArrayList<OrderCommodity>();
	

	public List<OrderStateRcd> getOrderStateRcds() {
		return orderStateRcds;
	}

	public void setOrderStateRcds(List<OrderStateRcd> orderStateRcds) {
		this.orderStateRcds = orderStateRcds;
	}

	public List<OrderCommodity> getOrderCommoditys() {
		return orderCommoditys;
	}

	public void setOrderCommoditys(List<OrderCommodity> orderCommoditys) {
		this.orderCommoditys = orderCommoditys;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	public Integer getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Long getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Long finishTime) {
		this.finishTime = finishTime;
	}

	
}
