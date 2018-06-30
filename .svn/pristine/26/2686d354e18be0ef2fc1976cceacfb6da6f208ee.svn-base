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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import com.alibaba.fastjson.annotation.JSONField;

/**
 * 图片资源表
 * 
 * @author sunchangjunn
 *
 */
@Entity
@Table(name = "t_image_resource")
public class ImageResource  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3793453109934748290L;
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "imageResource")
	private  List<CommodityRes> commodityResList;
	
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "imageResource")
	private List<Adv> advList;
	
	public List<Adv> getAdvList() {
		return advList;
	}
	public void setAdvList(List<Adv> advList) {
		this.advList = advList;
	}
	// 是否启用
	@Column(name = "enabled")
	private Integer  enabled;
	// 主键id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "image_resource_id")
	private Long imageResourceId;
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "imageResource")
	private  List<Commodity> commoditylist;
	
	public List<CommodityRes> getCommodityResList() {
		return commodityResList;
	}
	public void setCommodityResList(List<CommodityRes> commodityResList) {
		this.commodityResList = commodityResList;
	}
	public List<Commodity> getCommoditylist() {
		return commoditylist;
	}
	public void setCommoditylist(List<Commodity> commoditylist) {
		this.commoditylist = commoditylist;
	}
	// 备注
	@Column(name = "remarks")
	private String remarks;
	// 资源类型
	@Column(name = "res_type")
	private Short resType;
	// 缩略图
	@Column(name = "thump")
	private String thump;
	
	// 图片地址
	@Column(name = "url")
	private String url;
	
	

	public Long getImageResourceId() {
		return imageResourceId;
	}

	public String getRemarks() {
		return remarks;
	}
	public Short getResType() {
		return resType;
	}
	public String getThump() {
		return thump;
	}
	public String getUrl() {
		return url;
	}
	
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public void setImageResourceId(Long imageResourceId) {
		this.imageResourceId = imageResourceId;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setResType(Short resType) {
		this.resType = resType;
	}
	public void setThump(String thump) {
		this.thump = thump;
	}
	public void setUrl(String url) {
		this.url = url;
	}

}
