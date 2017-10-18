package jsonld2toplevel;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://www.w3.org/ns/shacl#PropertyShape")
public class PropertyShape {

	@PropertyTerm("http://raml.org/vocabularies/amf-validation#ramlPropertyId")
	protected String ramlProp;
	
	@PropertyTerm("http://www.w3.org/ns/shacl#pattern")
	protected String pattern;
}
