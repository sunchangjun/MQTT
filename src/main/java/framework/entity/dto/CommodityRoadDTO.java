package framework.entity.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

import framework.entity.po.Box;
import framework.entity.po.BoxPort;
import framework.entity.po.Commodity;
import framework.entity.po.CommodityRoad;
import framework.entity.po.ImageResource;

public class CommodityRoadDTO extends CommodityRoad {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -567278395455739729L;




	private Box box;




	BoxPort boxPort;

	




	private String code;


	private Commodity commodity;


	private Integer commodityNum;

	private Long commodityRoadId;


	private Integer  isEnable;


	private Double price;
	
	private  List<ImageResource> imageResourceList;

	public Box getBox() {
		return box;
	}

	public void setBox(Box box) {
		this.box = box;
	}

	public BoxPort getBoxPort() {
		return boxPort;
	}

	public void setBoxPort(BoxPort boxPort) {
		this.boxPort = boxPort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Commodity getCommodity() {
		return commodity;
	}

	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}

	public Integer getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(Integer commodityNum) {
		this.commodityNum = commodityNum;
	}

	public Long getCommodityRoadId() {
		return commodityRoadId;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public List<ImageResource> getImageResourceList() {
		return imageResourceList;
	}

	public void setImageResourceList(List<ImageResource> imageResourceList) {
		this.imageResourceList = imageResourceList;
	}
	
	
}
