package framework.entity.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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

@Entity
@Table(name = "t_order_commodity")
public class OrderCommodity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "bi_price")
	private Double biPrice;

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_id")
	private Box box;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_id")
	private Commodity commodity;

	@Column(name = "stocks")
	private Integer stocks;

	@Column(name = "ctime")
	private Long ctime;

	@Column(name = "dian_bi")
	private Double dianBi;

	@Column(name = "commodity_num")
	private Integer commodityNum;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "order_commodity_id")
	private Long orderCommodityId;

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	private Order order;

	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "orderCommodity")
	private List<OrderCommodityRoad> orderCommodityRoads = new ArrayList<OrderCommodityRoad>();

	// 商品价格
	@Column(name = "sale_price")
	private Double salePrice;
	
	
	@Column(name="cost_price")
	private Double costPrice;
	
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getGiveFen() {
		return giveFen;
	}

	public void setGiveFen(Double giveFen) {
		this.giveFen = giveFen;
	}

	@Column(name="give_fen")
	private Double giveFen;

	@Column(name = "stare")
	private Short stare;

	@Column(name = "utime")
	private Long utime;

	public Double getBiPrice() {
		return biPrice;
	}

	public void setBiPrice(Double biPrice) {
		this.biPrice = biPrice;
	}

	public Double getDianBi() {
		return dianBi;
	}

	public void setDianBi(Double dianBi) {
		this.dianBi = dianBi;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Box getBox() {
		return box;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public Long getCtime() {
		return ctime;
	}

	public Long getOrderCommodityId() {
		return orderCommodityId;
	}

	public Short getStare() {
		return stare;
	}

	public Long getUtime() {
		return utime;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Integer getStocks() {
		return stocks;
	}

	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}

	public Integer getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	public void setOrderCommodityId(Long orderCommodityId) {
		this.orderCommodityId = orderCommodityId;
	}

	public List<OrderCommodityRoad> getOrderCommodityRoads() {
		return orderCommodityRoads;
	}

	public void setOrderCommodityRoads(List<OrderCommodityRoad> orderCommodityRoads) {
		this.orderCommodityRoads = orderCommodityRoads;
	}

	public void setStare(Short stare) {
		this.stare = stare;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

}
