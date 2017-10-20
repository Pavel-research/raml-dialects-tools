package org.raml.vocabularies.dto;

import java.util.ArrayList;

public class PropertyTermDTO extends BaseTermDTO {

	private String range;
	protected ArrayList<String>sameAs=new ArrayList<String>();
	public ArrayList<String> getSameAs() {
		return sameAs;
	}
	public void setSameAs(ArrayList<String> sameAs) {
		this.sameAs = sameAs;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
}
