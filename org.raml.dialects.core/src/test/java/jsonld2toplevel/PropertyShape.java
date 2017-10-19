package jsonld2toplevel;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://www.w3.org/ns/shacl#PropertyShape")
public class PropertyShape {

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#ramlPropertyId")
	protected String ramlProp;
	
	@PropertyTerm("http://www.w3.org/ns/shacl#pattern")
	protected String pattern;
}
