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
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 货道表
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_commodity_road")
public class CommodityRoad implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_id")
	private Box box;

	@Column(name = "box_id", insertable = false, updatable = false)
	private Long boxId;

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public Long getBoxPortId() {
		return boxPortId;
	}

	public void setBoxPortId(Long boxPortId) {
		this.boxPortId = boxPortId;
	}

	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_port_id")
	private BoxPort boxPort;

	@Column(name = "box_port_id", insertable = false, updatable = false)
	private Long boxPortId;

	// 货道编码
	@Column(name = "code")
	private String code;

	@Column(name = "commodity_id", insertable = false, updatable = false)
	private Long commodityId;

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_id")
	private Commodity commodity;

	// 货道内商品数量
	@Column(name = "commodity_num")
	private Integer commodityNum;
	// 货道id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "commodity_road_id")
	private Long commodityRoadId;

	// 是否启用(-1:未启用;1:启用)
	@Column(name = "is_enable")
	private Integer  isEnable;

	public Box getBox() {
		return box;
	}

	public BoxPort getBoxPort() {
		return boxPort;
	}

	// 售货机串口编号

	public String getCode() {
		return code;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public Integer getCommodityNum() {
		return commodityNum;
	}

	public Long getCommodityRoadId() {
		return commodityRoadId;
	}



	public void setBox(Box box) {
		this.box = box;
	}

	public void setBoxPort(BoxPort boxPort) {
		this.boxPort = boxPort;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	public void setCommodityRoadId(Long commodityRoadId) {
		this.commodityRoadId = commodityRoadId;
	}

	public Integer getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}



}
