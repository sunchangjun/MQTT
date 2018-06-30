package framework.enums;


/**
 * 级别深度枚举
 * 1省级
 * 2市级
 * 3区县级
 * @author Administrator
 *
 */
public enum DepthEnum {

	
	PROVINCIAL(Short.parseShort("1"),"省级"),
	MUNICIPAL_LEVEL(Short.parseShort("2"),"市级"),
	COUNTY_LEVEL(Short.parseShort("3"),"县级"),
		;
		private Short type;
		private String 	description;

		private DepthEnum (Short type,String description) {
			this.type = type;
			this.description = description;
		}
		
		public Short getType() {
			return type;
		}
		public String getDescription() {
			return description;
		}
			
		public static DepthEnum getEnumByNumber(Short type){
			if (type == null)
	            return null;
	        for (DepthEnum depthEnum : DepthEnum.values()) {
	            if (depthEnum.getType().equals(type))
	                return depthEnum;
	        }
	        return null;
		}
		
		public static DepthEnum getEnumByDescription(String description){
			if (description == null)
	            return null;
	        for (DepthEnum depthEnum : DepthEnum.values()) {
	            if (depthEnum.getDescription().equals(description))
	                return depthEnum;
	        }
	        return null;
		}

}
