package jsonld2toplevel;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#Profile")
public class ValidationProfile2 {

	@PropertyTerm("http://schema.org/name")
	protected String name;

	@PropertyTerm("http://schema.org/description")
	protected String description;

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#extendsProfile")
	protected String profile;

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#version")
	protected String version;

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#setSeverityViolation")
	protected Set<String> violations = new LinkedHashSet<String>();

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#setSeverityInfo")
	protected Set<String> info = new LinkedHashSet<String>();

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#setSeverityWarning")
	protected Set<String> warnings = new LinkedHashSet<String>();

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#disableValidation")
	protected Set<String> disabled = new LinkedHashSet<String>();

	protected Map<String, ValidationRule> validationRules;

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#validations")
	@Hash("http://schema.org/name")
	public Map<String, ValidationRule> getValidationRules() {
		return validationRules;
	}

	public void setValidationRules(Map<String, ValidationRule> validationRules) {
		this.validationRules = validationRules;
	}
}