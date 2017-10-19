package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#FragmentsDeclaration")
public class FragmentsDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#encodes")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,Declaration>encodes;

	public Map<String, Declaration> getEncodes() {
		return encodes;
	}

	public void setEncodes(Map<String, Declaration> encodes) {
		this.encodes = encodes;
	}
}
