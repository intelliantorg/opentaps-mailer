package net.intelliant.imports.bean;

public class EntityFieldStatus {
	private String name;
	private String type;
	private boolean isNotNull;
	
	public EntityFieldStatus(String name, String type, boolean isNotNull) {
		this.name = name;
		this.type = type;
		this.isNotNull = isNotNull;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isNotNull() {
		return isNotNull;
	}

	public void setNotNull(boolean isNotNull) {
		this.isNotNull = isNotNull;
	}
}
