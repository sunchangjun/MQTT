package framework.entity.po;

import java.io.Serializable;
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

@Entity
@Table(name = "t_commodity_res")
public class CommodityRes implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = 4103439513004668811L;

		@JSONField(serialize = false)
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "commodity_id")
		private  Commodity commodity;
		
		@Column(name="commodity_id",insertable=false,updatable=false)
		private Long commodityId;
		
		
		public Long getCommodityId() {
			return commodityId;
		}

		public void setCommodityId(Long commodityId) {
			this.commodityId = commodityId;
		}

		public Long getImageResourceId() {
			return imageResourceId;
		}

		public void setImageResourceId(Long imageResourceId) {
			this.imageResourceId = imageResourceId;
		}

		public Long getImgResType() {
			return imgResType;
		}

		public void setImgResType(Long imgResType) {
			this.imgResType = imgResType;
		}

		//区域编号
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Id
		@Column(name="commodity_res_id")
		private  Long  commodityResId;
		

		@JSONField(serialize = false)
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "image_resource_id")
		private ImageResource imageResource;
		
		
		@Column(name="image_resource_id",insertable=false,updatable=false)
		private Long imageResourceId;
		
		@Column(name="img_res_type")
		private  Long  imgResType;

		public Commodity getCommodity() {
			return commodity;
		}

		public Long getCommodityResId() {
			return commodityResId;
		}

		public ImageResource getImageResource() {
			return imageResource;
		}

		public void setCommodity(Commodity commodity) {
			this.commodity = commodity;
		}

		public void setCommodityResId(Long commodityResId) {
			this.commodityResId = commodityResId;
		}

		public void setImageResource(ImageResource imageResource) {
			this.imageResource = imageResource;
		}



	
		
		
		
	

}
