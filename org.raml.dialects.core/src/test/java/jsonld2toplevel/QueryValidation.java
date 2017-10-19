package jsonld2toplevel;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Mandatory;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#QueryValidation")
public class QueryValidation extends ValidationRule{

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#targetQuery")
	@Mandatory
	String targetQuery;
}
