package org.raml.dialects.toplevel.model;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#Document")
public class Raml {

	@PropertyTerm("http://raml.org/vocabularies/meta#module")
	ModuleDeclaration module;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#document")
	DocumentDeclaration document;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#fragments")
	protected FragmentsDeclaration fragments;
}
