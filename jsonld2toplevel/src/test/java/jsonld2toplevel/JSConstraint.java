package jsonld2toplevel;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://www.w3.org/ns/shacl#JSConstraint")
public class JSConstraint {

	@PropertyTerm("http://www.w3.org/ns/shacl#jsCode")
	String jsCode;
}
