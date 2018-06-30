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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 补货计划表
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_replenishment_plan")
public class ReplenishmentPlan implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7768439776925575690L;
	//主键id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name="replenishment_plan_id")
	private Long replenishmentPlanId;
	
	//端口号
	@JSONField(serialize = false)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "box_port_id")
	private  BoxPort boxPort;
	
	
	@JSONField(serialize = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "replenishmentPlan")
	List<ReplenishmentPlanCommodity> replenishmentPlanCommoditys=new ArrayList<ReplenishmentPlanCommodity>();
	
	
	
	
	
	public List<ReplenishmentPlanCommodity> getReplenishmentPlanCommoditys() {
		return replenishmentPlanCommoditys;
	}
	public void setReplenishmentPlanCommoditys(List<ReplenishmentPlanCommodity> replenishmentPlanCommoditys) {
		this.replenishmentPlanCommoditys = replenishmentPlanCommoditys;
	}
	//创建时间
	@Column(name="create_time")
	private Long createTime;
	//执行时间
	@Column(name="execution_time")
	private Long executionTime;
	//执行状态
	@Column(name="execution_status")
	private Integer executionStatus;
	//是否启用
	@Column(name="is_enable")
	private Integer isEnable;
	public Long getReplenishmentPlanId() {
		return replenishmentPlanId;
	}
	public void setReplenishmentPlanId(Long replenishmentPlanId) {
		this.replenishmentPlanId = replenishmentPlanId;
	}

	public BoxPort getBoxPort() {
		return boxPort;
	}
	public void setBoxPort(BoxPort boxPort) {
		this.boxPort = boxPort;
	}
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getExecutionTime() {
		return executionTime;
	}
	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}
	public Integer getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(Integer executionStatus) {
		this.executionStatus = executionStatus;
	}
	public Integer getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Integer isEnable) {
		this.isEnable = isEnable;
	}
	
	

	
	

}
