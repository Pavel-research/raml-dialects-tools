package jsonld2toplevel;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Mandatory;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#QueryValidation")
public class QueryValidation extends ValidationRule{

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#targetQuery")
	@Mandatory
	String targetQuery;
}
