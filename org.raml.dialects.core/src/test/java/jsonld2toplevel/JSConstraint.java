package jsonld2toplevel;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://www.w3.org/ns/shacl#JSConstraint")
public class JSConstraint {

	@PropertyTerm("http://www.w3.org/ns/shacl#jsCode")
	String jsCode;
}
