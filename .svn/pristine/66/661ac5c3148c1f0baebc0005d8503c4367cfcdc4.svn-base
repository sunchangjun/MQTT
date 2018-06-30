package framework.entity.po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 商品表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_commodity")
public class Commodity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8515573585390951519L;

	@Column(name="bi_price")
	private Double biPrice;
	
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_cate_id")
	private  CommodityCate commodityCate;
	

	@Column(name="commodity_cate_id",insertable=false,updatable=false)
	private Long commodityCateId;
	
	//商品描述
	@Column(name="commodity_desc")
	private String commodityDesc;
	
	//商品编号
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name="commodity_id")
	private Long commodityId;
	
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodity")
	private List<CommodityBoxPortCateRel> commodityBoxPortCateRels = new ArrayList<CommodityBoxPortCateRel>();
	
	
	//商品名称
	@Column(name="commodity_name")
	private String commodityName;
	
	
	//商品货号
	@Column(name="commodity_no")
	private String commodityNo;
	
	
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_resource_id")
	private ImageResource imageResource;
	

	@Column(name = "image_resource_id",insertable=false,updatable=false)
	private Long imageResourceId;
	
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodity")
	private  List<CommodityRes> commodityResList;
	
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "commodity")
	private List<CommodityRoad> commodityRoadList;
	
	//净含量
	@Column(name="content")
	private String content;
	
	//创建时间
	@Column(name="create_time")
	private Long createTime;
	
	@Column(name="dian_bi")
	private Double dianBi;
	
	//是否有效
	@Column(name="enabled")
	private Integer enabled;
	
	//商品价格
	@Column(name="price")
	private Double price;
	
	//规格
	@Column(name="specification")
	private String specification;
	
	//库存
	@Column(name="stocks")
	private  Integer  stocks;
	
	
	//累计销售量
	@Column(name="total_sale_num")
	private Integer totalSaleNum;
	
	public CommodityCate getCommodityCate() {
		return commodityCate;
	}

	public void setCommodityCate(CommodityCate commodityCate) {
		this.commodityCate = commodityCate;
	}

	public Long getCommodityCateId() {
		return commodityCateId;
	}

	public void setCommodityCateId(Long commodityCateId) {
		this.commodityCateId = commodityCateId;
	}

	public ImageResource getImageResource() {
		return imageResource;
	}

	public void setImageResource(ImageResource imageResource) {
		this.imageResource = imageResource;
	}

	public Long getImageResourceId() {
		return imageResourceId;
	}

	public void setImageResourceId(Long imageResourceId) {
		this.imageResourceId = imageResourceId;
	}

	public List<CommodityRoad> getCommodityRoadList() {
		return commodityRoadList;
	}

	public void setCommodityRoadList(List<CommodityRoad> commodityRoadList) {
		this.commodityRoadList = commodityRoadList;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public Double getBiPrice() {
		return biPrice;
	}

	public String getCommodityDesc() {
		return commodityDesc;
	}

	public Long getCommodityId() {
		return commodityId;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public List<CommodityRes> getCommodityResList() {
		return commodityResList;
	}

	public String getContent() {
		return content;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public Double getDianBi() {
		return dianBi;
	}



	public List<CommodityBoxPortCateRel> getCommodityBoxPortCateRels() {
		return commodityBoxPortCateRels;
	}

	public void setCommodityBoxPortCateRels(List<CommodityBoxPortCateRel> commodityBoxPortCateRels) {
		this.commodityBoxPortCateRels = commodityBoxPortCateRels;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getStocks() {
		return stocks;
	}

	public Integer getTotalSaleNum() {
		return totalSaleNum;
	}

	public void setBiPrice(Double biPrice) {
		this.biPrice = biPrice;
	}


	public void setCommodityDesc(String commodityDesc) {
		this.commodityDesc = commodityDesc;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public void setCommodityResList(List<CommodityRes> commodityResList) {
		this.commodityResList = commodityResList;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public void setDianBi(Double dianBi) {
		this.dianBi = dianBi;
	}


	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setStocks(Integer stocks) {
		this.stocks = stocks;
	}

	public void setTotalSaleNum(Integer totalSaleNum) {
		this.totalSaleNum = totalSaleNum;
	}

	
	
	
}
