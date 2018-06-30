package framework.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 资源回收表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_recycle")
public class Recycle implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789833282013027754L;

	//主键id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name="id")
	private Long id;
	
	//商品id
	@Column(name="commodity_id")
	private Long commodityId;
	
	//数量
	@Column(name="num")
	private Integer num;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Long getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
	

}
