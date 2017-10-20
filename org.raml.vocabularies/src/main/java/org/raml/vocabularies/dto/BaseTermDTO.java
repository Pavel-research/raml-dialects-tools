package org.raml.vocabularies.dto;

public class BaseTermDTO {

	private String displayName;
	private String description;
	private Object extendedElement;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BaseTermDTO() {
		super();
	}

	public Object getExtends() {
		return extendedElement;
	}

	public void setExtends(Object extendedElement) {
		this.extendedElement = extendedElement;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}