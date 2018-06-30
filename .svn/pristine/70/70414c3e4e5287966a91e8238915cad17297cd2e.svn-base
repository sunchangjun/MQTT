package framework.enums;



/**
 * 枚举对应表:t_order_state,
 * 注意维护枚举,当数据库发生变化时,此枚举随数据库状态变化
 * @author Administrator
 *
 */

public enum OrderStatusEnum {

	
	
	PRE_PAYMENT(1L,"待付款"),
	WAITING_FOR_SHIPMENT(2L,"待出货"),
	TRANSACTION_COMPLETE(3L,"交易完成"),
	REFUNDING(4L,"退款中"),
	REFUNDED(5L,"已退款"),
	TRADING_CLOSED(6L,"交易关闭"),
		;
		private Long type;
		private String 	description;

		private OrderStatusEnum (Long type,String description) {
			this.type = type;
			this.description = description;
		}
		
		public Long getType() {
			return type;
		}
		public String getDescription() {
			return description;
		}
			
		public static OrderStatusEnum getEnumByNumber(Long type){
			if (type == null)
	            return null;
	        for (OrderStatusEnum depthEnum : OrderStatusEnum.values()) {
	            if (depthEnum.getType().equals(type))
	                return depthEnum;
	        }
	        return null;
		}
		
		public static OrderStatusEnum getEnumByDescription(String description){
			if (description == null)
	            return null;
	        for (OrderStatusEnum depthEnum : OrderStatusEnum.values()) {
	            if (depthEnum.getDescription().equals(description))
	                return depthEnum;
	        }
	        return null;
		}

}
