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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "t_box_port_cate")
public class BoxPortCate  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "box_port_cate_id")
	private Long boxPortCateId;

	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "boxPortCate")
	private List<BoxPort> boxPorts = new ArrayList<BoxPort>();

	@Column(name = "box_port_cate_name")
	private String boxPortCateName;
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "boxPortCate")
	private List<CommodityBoxPortCateRel> commodityBoxPortCateRels = new ArrayList<CommodityBoxPortCateRel>();

	public Long getBoxPortCateId() {
		return boxPortCateId;
	}

	public void setBoxPortCateId(Long boxPortCateId) {
		this.boxPortCateId = boxPortCateId;
	}

	public List<BoxPort> getBoxPorts() {
		return boxPorts;
	}

	public void setBoxPorts(List<BoxPort> boxPorts) {
		this.boxPorts = boxPorts;
	}

	public String getBoxPortCateName() {
		return boxPortCateName;
	}

	public void setBoxPortCateName(String boxPortCateName) {
		this.boxPortCateName = boxPortCateName;
	}

	public List<CommodityBoxPortCateRel> getCommodityBoxPortCateRels() {
		return commodityBoxPortCateRels;
	}

	public void setCommodityBoxPortCateRels(List<CommodityBoxPortCateRel> commodityBoxPortCateRels) {
		this.commodityBoxPortCateRels = commodityBoxPortCateRels;
	}

	
	
	

}
