package jsonld2toplevel;

import java.util.ArrayList;

import org.raml.dialects.core.annotations.AlsoMappedTo;
import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/amf-validation#ShapeValidation")
@AlsoMappedTo({ FunctionValidation.class, QueryValidation.class })
public class ValidationRule {

	@PropertyTerm("http://schema.org/name")
	protected String name;

	@PropertyTerm("http://www.w3.org/ns/shacl#message")
	protected String message;
	
	@PropertyTerm("http://raml.org/vocabularies/amf-validation#ramlClassId")
	protected String classId;
	
	@PropertyTerm("http://www.w3.org/ns/shacl#property")
	@Hash("http://raml.org/vocabularies/amf-validation#ramlPropertyId")
	protected ArrayList<PropertyShape> property=new ArrayList<PropertyShape>();
}