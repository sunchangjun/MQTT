package framework.entity.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

@Entity
@Table(name = "t_order_state_rcd")
public class OrderStateRcd implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3797134911186251518L;

	//主键id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Id
		@Column(name="order_state_rcd_id")
		private Long orderStateRcdId;
		
		
		@JSONField(serialize = false)
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "order_state_id")
		private OrderState orderState;
		
		
		public OrderState getOrderState() {
			return this.orderState;
		}

		public void setOrderState(OrderState orderState) {
			this.orderState = orderState;
		}
		
		
		@JSONField(serialize = false)
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "order_id")
		private Order order;

		
		public Order getOrder() {
			return order;
		}

		public void setOrder(Order order) {
			this.order = order;
		}

		//订单状态
		@Column(name="order_state_id" , insertable = false, updatable = false)
		private Long orderStateId;
		
		//记录时间
		@Column(name="rcd_time")
		private Long rcdTime;
		
		//备注
		@Column(name="remarks")
		private String remarks;



		public Long getOrderStateRcdId() {
			return orderStateRcdId;
		}

		public void setOrderStateRcdId(Long orderStateRcdId) {
			this.orderStateRcdId = orderStateRcdId;
		}


		public Long getOrderStateId() {
			return orderStateId;
		}

		public void setOrderStateId(Long orderStateId) {
			this.orderStateId = orderStateId;
		}

		public Long getRcdTime() {
			return rcdTime;
		}

		public void setRcdTime(Long rcdTime) {
			this.rcdTime = rcdTime;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		
		
		
		
}
