package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Hash;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#FragmentsDeclaration")
public class FragmentsDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#encodes")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,Declaration>encodes;
}
