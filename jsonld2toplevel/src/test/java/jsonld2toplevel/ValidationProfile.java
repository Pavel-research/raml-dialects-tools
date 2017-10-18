package jsonld2toplevel;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#Profile")
public class ValidationProfile {

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

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#validations")
	protected ArrayList<ValidationRule> validationRules = new ArrayList<ValidationRule>();
}