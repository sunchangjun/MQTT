package framework.entity.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import framework.entity.po.Box;
import framework.entity.po.BoxPort;
import framework.entity.po.Commodity;
import framework.entity.po.CommodityRoad;

public class BoxDTO extends Box {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5214285742601649813L;
	
	//售空商品
	@Transient
	List<Commodity> outOffName ;
	
	// 现有库存
	@Transient
	public Integer existingStocks;

	// 空货道数量
	@Transient
	private Integer nullRoadNumber;

	// 百分比1
	@Transient
	public String percentage1;

	// 百分比2
	@Transient
	public String percentage2;
	// 百分比3
	@Transient
	public String percentage3;
	
	// 缺货商品名称
	@Transient
	public List<Commodity> outOfStockName;

	// 配货商品名称
	@Transient
	public List<String> pickingName;
	
	// 库存商品总数
	@Transient
	public Integer sumStocks;

	// 在售商品种类
	@Transient
	private Integer typeOfSale;
	
	

	private String address;

	private Long areaId;

	private String boxCode;

	private Long boxId;

	private String boxKey;

	private List<BoxPort> boxPorts = new ArrayList<BoxPort>();

	private List<CommodityRoad> commodityRoads = new ArrayList<CommodityRoad>(0);

	// 货道数量
	private Integer cargoRoadNumber;

	// 售货机二维码
	private String codeImgUrl;

	// 创建时间
	private Long ctime;

	// 是否启用(-1:维护中,未启用1:正常2:故障中,等待维护)
	private Integer  status;

	// 修改时间
	private Long utime;

	
	



	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getBoxCode() {
		return boxCode;
	}

	public void setBoxCode(String boxCode) {
		this.boxCode = boxCode;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBoxKey() {
		return boxKey;
	}

	public void setBoxKey(String boxKey) {
		this.boxKey = boxKey;
	}

	public List<BoxPort> getBoxPorts() {
		return boxPorts;
	}

	public void setBoxPorts(List<BoxPort> boxPorts) {
		this.boxPorts = boxPorts;
	}

	public List<CommodityRoad> getCommodityRoads() {
		return commodityRoads;
	}

	public void setCommodityRoads(List<CommodityRoad> commodityRoads) {
		this.commodityRoads = commodityRoads;
	}

	public Integer getCargoRoadNumber() {
		return cargoRoadNumber;
	}

	public void setCargoRoadNumber(Integer cargoRoadNumber) {
		this.cargoRoadNumber = cargoRoadNumber;
	}

	public String getCodeImgUrl() {
		return codeImgUrl;
	}

	public void setCodeImgUrl(String codeImgUrl) {
		this.codeImgUrl = codeImgUrl;
	}

	public Long getCtime() {
		return ctime;
	}

	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}





	public Long getUtime() {
		return utime;
	}

	public void setUtime(Long utime) {
		this.utime = utime;
	}

	public Integer getSumStocks() {
		return sumStocks;
	}

	public void setSumStocks(Integer sumStocks) {
		this.sumStocks = sumStocks;
	}

	public Integer getTypeOfSale() {
		return typeOfSale;
	}

	public void setTypeOfSale(Integer typeOfSale) {
		this.typeOfSale = typeOfSale;
	}

	public List<Commodity> getOutOffName() {
		return outOffName;
	}

	public void setOutOffName(List<Commodity> outOffName) {
		this.outOffName = outOffName;
	}

	public Integer getExistingStocks() {
		return existingStocks;
	}

	public void setExistingStocks(Integer existingStocks) {
		this.existingStocks = existingStocks;
	}

	public Integer getNullRoadNumber() {
		return nullRoadNumber;
	}

	public void setNullRoadNumber(Integer nullRoadNumber) {
		this.nullRoadNumber = nullRoadNumber;
	}

	public String getPercentage1() {
		return percentage1;
	}

	public void setPercentage1(String percentage1) {
		this.percentage1 = percentage1;
	}

	public String getPercentage2() {
		return percentage2;
	}

	public void setPercentage2(String percentage2) {
		this.percentage2 = percentage2;
	}

	public String getPercentage3() {
		return percentage3;
	}

	public void setPercentage3(String percentage3) {
		this.percentage3 = percentage3;
	}

	public List<Commodity> getOutOfStockName() {
		return outOfStockName;
	}

	public void setOutOfStockName(List<Commodity> outOfStockName) {
		this.outOfStockName = outOfStockName;
	}

	public List<String> getPickingName() {
		return pickingName;
	}

	public void setPickingName(List<String> pickingName) {
		this.pickingName = pickingName;
	}

	
	
}
