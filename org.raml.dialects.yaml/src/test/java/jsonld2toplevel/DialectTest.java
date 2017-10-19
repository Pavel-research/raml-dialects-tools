package jsonld2toplevel;

import java.util.Map;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.DomainRootElement;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;
import org.raml.vocabularies.Vocabulary;

@ClassTerm("http://raml.org/vocabularies/meta#dialect")
@DomainRootElement(dependencies=Vocabulary.class)
public class DialectTest {

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
	protected Map<String,NodeMappingTest>nodeMappings;
	
	
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

	public Map<String, NodeMappingTest> getNodeMappings() {
		return nodeMappings;
	}

	public void setNodeMappings(Map<String, NodeMappingTest> nodeMappings) {
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
