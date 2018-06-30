package framework.entity.po;

import java.io.Serializable;
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
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;
@Entity
@Table(name = "t_adv")
public class Adv implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3965940965475456678L;
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name="adv_id")
	private Long advId;

	@Column(name="image_resource_id",insertable=false,updatable=false)
	private Long imageResourceId;
	
	
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_resource_id" )
	private ImageResource imageResource;
	
	@Column(name="sort_order")
	private Integer sortOrder;
	
	@Column(name="type")
	private Integer type;
	
	@Column(name="link_url")
	private String linkUrl; 
	
	@Column(name="area_id",insertable=false,updatable=false)
	private Long areaId;
	

	
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "area_id" )
	private Area area;

	public Long getAdvId() {
		return advId;
	}

	public void setAdvId(Long advId) {
		this.advId = advId;
	}

	public Long getImageResourceId() {
		return imageResourceId;
	}

	public void setImageResourceId(Long imageResourceId) {
		this.imageResourceId = imageResourceId;
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public void setImageResource(ImageResource imageResource) {
		this.imageResource = imageResource;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}



	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	


	
	
	
	

}
