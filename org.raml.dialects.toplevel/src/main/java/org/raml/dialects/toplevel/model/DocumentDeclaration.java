package org.raml.dialects.toplevel.model;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;
import org.raml.jsonld2toplevel.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#DocumentContentDeclaration")
public class DocumentDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#encodes")
	@Reference
	protected NodeMapping encodes;

	public NodeMapping getEncodes() {
		return encodes;
	}

	public void setEncodes(NodeMapping encodes) {
		this.encodes = encodes;
	}
}
