package jsonld2toplevel;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.DialectPropertyName;
import org.raml.jsonld2toplevel.annotations.Mandatory;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#FunctionValidation")
public class FunctionValidation extends ValidationRule{

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#ramlClassId")
	String classId;
	
	
	@PropertyTerm("http://www.w3.org/ns/shacl#js")
	@DialectPropertyName("functionConstraint")
	@Mandatory
	JSConstraint constraint;
}
