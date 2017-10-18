package org.raml.dialects.toplevel.model;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#Declaration")
public class Declaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#declaredNode")
	protected String declaredNode;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;
}
