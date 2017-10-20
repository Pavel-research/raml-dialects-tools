package org.raml.vocabularies.dto;

import java.util.ArrayList;

public class ClassTermDTO extends BaseTermDTO{

	private ArrayList<String>properties=new ArrayList<String>();

	public ArrayList<String> getProperties() {
		return properties;
	}

	public void setProperties(ArrayList<String> properties) {
		this.properties = properties;
	}
}
