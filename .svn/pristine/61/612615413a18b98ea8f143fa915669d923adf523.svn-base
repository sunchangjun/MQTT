package framework.enums;

public enum IsDeleteEnum {

	
		UN_DELETE(0,"启用"),
		DELETE(1,"删除"),
		;
		private Integer type;
		private String 	description;

		private IsDeleteEnum (Integer type,String description) {
			this.type = type;
			this.description = description;
		}
		
		public Integer getType() {
			return type;
		}
		public String getDescription() {
			return description;
		}
			
		public static IsDeleteEnum getEnumByNumber(Integer type){
			if (type == null)
	            return null;
	        for (IsDeleteEnum isDeleteEnum : IsDeleteEnum.values()) {
	            if (isDeleteEnum.getType().equals(type))
	                return isDeleteEnum;
	        }
	        return null;
		}
		
		public static IsDeleteEnum getEnumByDescription(String description){
			if (description == null)
	            return null;
	        for (IsDeleteEnum isDeleteEnum : IsDeleteEnum.values()) {
	            if (isDeleteEnum.getDescription().equals(description))
	                return isDeleteEnum;
	        }
	        return null;
		}

}
