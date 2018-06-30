package framework.entity.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.annotation.JSONField;

import framework.entity.po.Box;
import framework.entity.po.BoxPort;
import framework.entity.po.CommodityRoad;

public class BoxPortDTO extends BoxPort {
	
	private JSONArray  jsonArray;


	

	private Long boxId;
	
	// 串口编码
	private String devicePort;

	// 是否启用(-1:未启用;1:启用)
	private Short isEnable;
	
	

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}


	private Long boxPortId;







	public Long getBoxPortId() {
		return boxPortId;
	}



	public String getDevicePort() {
		return devicePort;
	}

	public Short getIsEnable() {
		return isEnable;
	}

	

	public void setBoxPortId(Long boxPortId) {
		this.boxPortId = boxPortId;
	}



	public void setDevicePort(String devicePort) {
		this.devicePort = devicePort;
	}

	public void setIsEnable(Short isEnable) {
		this.isEnable = isEnable;
	}
}
