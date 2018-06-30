package framework.enums;

public enum EnabledEnum {

	ENABLED(1, "启用"), NO_ENABLED(0, "未启用"),;
	private Integer type;
	private String description;

	private EnabledEnum(Integer type, String description) {
		this.type = type;
		this.description = description;
	}

	public Integer getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public static EnabledEnum getEnumByNumber(Integer type) {
		if (type == null)
			return null;
		for (EnabledEnum enabledEnum : EnabledEnum.values()) {
			if (enabledEnum.getType().equals(type))
				return enabledEnum;
		}
		return null;
	}

	public static EnabledEnum getEnumByDescription(String description) {
		if (description == null)
			return null;
		for (EnabledEnum enabledEnum : EnabledEnum.values()) {
			if (enabledEnum.getDescription().equals(description))
				return enabledEnum;
		}
		return null;
	}

}
