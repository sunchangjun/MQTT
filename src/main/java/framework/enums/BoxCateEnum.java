package framework.enums;


/**
 * 售货机分类(参考表t_box_cate)
 * @author Administrator
 *
 */
public enum BoxCateEnum {

	
	NORMAL_TEMPERATURE(2L,"常温柜"),
	REFRIGERATION(1L,"冷柜"),
	HEATING(3L,"热柜"),
		;
		private Long type;
		private String 	description;

		private BoxCateEnum (Long type,String description) {
			this.type = type;
			this.description = description;
		}
		
		public Long getType() {
			return type;
		}
		public String getDescription() {
			return description;
		}
			
		public static BoxCateEnum getEnumByNumber(Long type){
			if (type == null)
	            return null;
	        for (BoxCateEnum depthEnum : BoxCateEnum.values()) {
	            if (depthEnum.getType().equals(type))
	                return depthEnum;
	        }
	        return null;
		}
		
		public static BoxCateEnum getEnumByDescription(String description){
			if (description == null)
	            return null;
	        for (BoxCateEnum depthEnum : BoxCateEnum.values()) {
	            if (depthEnum.getDescription().equals(description))
	                return depthEnum;
	        }
	        return null;
		}

}
