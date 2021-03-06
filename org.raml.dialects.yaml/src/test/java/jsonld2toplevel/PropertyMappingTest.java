package jsonld2toplevel;

import java.util.ArrayList;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.DialectPropertyName;
import org.raml.dialects.core.annotations.NeedsResolving;
import org.raml.dialects.core.annotations.PropertyTerm;
import org.raml.dialects.core.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#PropertyMapping")
public class PropertyMappingTest {

	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;

	@PropertyTerm("http://raml.org/vocabularies/meta#propertyTerm")
	@NeedsResolving
	protected String propertyTerm;

	@PropertyTerm("http://raml.org/vocabularies/meta#hash")
	@NeedsResolving
	protected String hash;

	@PropertyTerm("http://raml.org/vocabularies/meta#pattern")
	protected String pattern;

	@PropertyTerm("http://raml.org/vocabularies/meta#mandatory")
	protected boolean mandatory;

	@PropertyTerm("http://raml.org/vocabularies/meta#allowMultiple")
	protected boolean allowMultiple;

	@PropertyTerm("http://raml.org/vocabularies/meta#asMap")
	protected boolean asMap;

	@PropertyTerm("http://raml.org/vocabularies/meta#range")
	@Reference
	protected ArrayList<NodeMappingTest> range;

	@PropertyTerm("http://raml.org/vocabularies/meta#enum")
	@DialectPropertyName("enum")
	protected ArrayList<String> enumValues;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPropertyTerm() {
		return propertyTerm;
	}

	public void setPropertyTerm(String propertyTerm) {
		this.propertyTerm = propertyTerm;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	public boolean isAllowMultiple() {
		return allowMultiple;
	}

	public void setAllowMultiple(boolean allowMultiple) {
		this.allowMultiple = allowMultiple;
	}

	public boolean isAsMap() {
		return asMap;
	}

	public void setAsMap(boolean asMap) {
		this.asMap = asMap;
	}

	public ArrayList<NodeMappingTest> getRange() {
		return range;
	}

	public void setRange(ArrayList<NodeMappingTest> range) {
		this.range = range;
	}

	public ArrayList<String> getEnumValues() {
		return enumValues;
	}

	public void setEnumValues(ArrayList<String> enumValues) {
		this.enumValues = enumValues;
	}
}