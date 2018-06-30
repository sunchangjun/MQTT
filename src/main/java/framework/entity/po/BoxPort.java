package framework.entity.po;

import java.io.Serializable;
import java.util.HashSet;
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
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 串口
 * 
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_box_port")
public class BoxPort implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BoxPortCate getBoxPortCate() {
		return boxPortCate;
	}

	public void setBoxPortCate(BoxPortCate boxPortCate) {
		this.boxPortCate = boxPortCate;
	}

	public Long getBoxPortCateId() {
		return boxPortCateId;
	}

	public void setBoxPortCateId(Long boxPortCateId) {
		this.boxPortCateId = boxPortCateId;
	}

	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_id" )
	private Box box;
	
	@Column(name="box_id",insertable=false,updatable=false)
	private Long boxId;

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	// 售货机串口编号
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "box_port_id")
	private Long boxPortId;
	
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_port_cate_id" )
	private BoxPortCate boxPortCate;
	
	@Column(name="box_port_cate_id",insertable=false,updatable=false)
	private Long boxPortCateId;

	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "boxPort")
	private Set<CommodityRoad> commodityRoads = new HashSet<CommodityRoad>(0);

	// 串口编码
	@Column(name = "device_port")
	private String devicePort;

	// 是否启用(-1:未启用;1:启用)
	@Column(name = "is_enable")
	private Short isEnable;

	public Box getBox() {
		return box;
	}

	public Long getBoxPortId() {
		return boxPortId;
	}

	public Set<CommodityRoad> getCommodityRoads() {
		return commodityRoads;
	}

	public String getDevicePort() {
		return devicePort;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public void setBoxPortId(Long boxPortId) {
		this.boxPortId = boxPortId;
	}

	public void setCommodityRoads(Set<CommodityRoad> commodityRoads) {
		this.commodityRoads = commodityRoads;
	}

	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}

}
