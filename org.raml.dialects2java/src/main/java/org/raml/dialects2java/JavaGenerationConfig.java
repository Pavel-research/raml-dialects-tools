package org.raml.dialects2java;

public class JavaGenerationConfig {

	private String packageName;

	public void setDefaultPackageName(String string) {
		this.packageName=string;
	}
	
	public String getDefailtPackageName(){
		return this.packageName;
	}

	public boolean isGenerateBuilderMethods() {
		return false;
	}

}
