package jsonld2toplevel;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.PropertyTerm;
import org.raml.dialects.core.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#DocumentContentDeclaration")
public class DocumentDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#encodes")
	@Reference
	protected NodeMappingTest encodes;

	public NodeMappingTest getEncodes() {
		return encodes;
	}

	public void setEncodes(NodeMappingTest encodes) {
		this.encodes = encodes;
	}
}
