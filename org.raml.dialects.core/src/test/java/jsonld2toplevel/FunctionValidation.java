package jsonld2toplevel;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.DialectPropertyName;
import org.raml.dialects.core.annotations.Mandatory;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#FunctionValidation")
public class FunctionValidation extends ValidationRule{

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#ramlClassId")
	String classId;
	
	
	@PropertyTerm("http://www.w3.org/ns/shacl#js")
	@DialectPropertyName("functionConstraint")
	@Mandatory
	JSConstraint constraint;
}
