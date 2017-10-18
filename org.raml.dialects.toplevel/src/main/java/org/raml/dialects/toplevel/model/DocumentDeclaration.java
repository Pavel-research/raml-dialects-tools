package org.raml.dialects.toplevel.model;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#DocumentContentDeclaration")
public class DocumentDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#encodes")
	String encodes;
}
