package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Hash;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;
import org.raml.jsonld2toplevel.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#NodeDefinition")
public class NodeMapping {

	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#classTerm")
	@Reference()
	protected String classTerm;
	
	@Hash("http://raml.org/vocabularies/meta#name")
	@PropertyTerm("http://raml.org/vocabularies/meta#mapping")
	protected Map<String,PropertyMapping>mappings;
}
