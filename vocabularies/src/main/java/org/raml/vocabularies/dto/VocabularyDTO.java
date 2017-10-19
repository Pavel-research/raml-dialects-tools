package org.raml.vocabularies.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class VocabularyDTO {

	private String base;
	
	private String vocabulary;
	
	private String version;
	
	private String usage;
	
    private Map<String,String>uses=new LinkedHashMap<String, String>();
	
	private Map<String,String>external=new LinkedHashMap<String, String>();

	private Map<String,PropertyTermDTO>propertyTerms=new LinkedHashMap<String, PropertyTermDTO>();
	
	private Map<String,ClassTermDTO>classTerms=new LinkedHashMap<String, ClassTermDTO>();
	
	public String getUsage() {
		return usage;
	}
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	public String getVocabulary() {
		return vocabulary;
	}
	public void setVocabulary(String dialect) {
		this.vocabulary = dialect;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Map<String, String> getUses() {
		return uses;
	}
	public void setUses(Map<String, String> uses) {
		this.uses = uses;
	}
	public Map<String, String> getExternal() {
		return external;
	}
	public void setExternal(Map<String, String> external) {
		this.external = external;
	}
	public Map<String, PropertyTermDTO> getPropertyTerms() {
		return propertyTerms;
	}
	public void setPropertyTerms(Map<String, PropertyTermDTO> propertyTerms) {
		this.propertyTerms = propertyTerms;
	}
	public Map<String, ClassTermDTO> getClassTerms() {
		return classTerms;
	}
	public void setClassTerms(Map<String, ClassTermDTO> classTerms) {
		this.classTerms = classTerms;
	}	
}