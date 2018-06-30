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
 * 商品分类表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_commodity_cate")
public class CommodityCate implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2893030831861395477L;
	//分类别名
	@Column(name="commodity_cate_alias")
	private String commodityCateAlias;
	//分类id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name="commodity_cate_id")
	private Long commodityCateId;
	public List<Commodity> getCommoditylist() {
		return commoditylist;
	}
	public void setCommoditylist(List<Commodity> commoditylist) {
		this.commoditylist = commoditylist;
	}
	//分类名称
	@Column(name="commodity_cate_name")
	private String commodityCateName;
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodityCate")
	private  List<Commodity> commoditylist;
	
	

	
	//分类图片
	@Column(name="pic_url")
	private String picUrl;
	
	public String getCommodityCateAlias() {
		return commodityCateAlias;
	}
	public Long getCommodityCateId() {
		return commodityCateId;
	}
	public String getCommodityCateName() {
		return commodityCateName;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setCommodityCateAlias(String commodityCateAlias) {
		this.commodityCateAlias = commodityCateAlias;
	}
	public void setCommodityCateId(Long commodityCateId) {
		this.commodityCateId = commodityCateId;
	}
	public void setCommodityCateName(String commodityCateName) {
		this.commodityCateName = commodityCateName;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}


	
	

}
