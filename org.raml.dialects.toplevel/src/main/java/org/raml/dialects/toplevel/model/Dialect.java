package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#dialect")
public class Dialect {

	@PropertyTerm("http://raml.org/vocabularies/meta#dialect")
	protected String dialect;

	@PropertyTerm("http://raml.org/vocabularies/meta#version")
	protected String version;

	@PropertyTerm("http://schema.org/description")
	protected String description;

	@PropertyTerm("http://raml.org/vocabularies/meta#raml")
	protected Raml raml;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#nodeMappings")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,NodeMapping>nodeMappings;
	
	
	@PropertyTerm("http://raml.org/vocabularies/meta#vocabularies")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,External>vocabularies;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#external")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,External>external;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Raml getRaml() {
		return raml;
	}

	public void setRaml(Raml raml) {
		this.raml = raml;
	}

	public Map<String, NodeMapping> getNodeMappings() {
		return nodeMappings;
	}

	public void setNodeMappings(Map<String, NodeMapping> nodeMappings) {
		this.nodeMappings = nodeMappings;
	}

	public Map<String, External> getVocabularies() {
		return vocabularies;
	}

	public void setVocabularies(Map<String, External> vocabularies) {
		this.vocabularies = vocabularies;
	}

	public Map<String, External> getExternal() {
		return external;
	}

	public void setExternal(Map<String, External> external) {
		this.external = external;
	}
}
