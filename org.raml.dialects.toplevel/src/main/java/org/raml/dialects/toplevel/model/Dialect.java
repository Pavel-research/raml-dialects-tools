package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Hash;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#dialect")
public class Dialect {

	@PropertyTerm("http://raml.org/vocabularies/meta#dialect")
	protected String dialect;

	@PropertyTerm("http://raml.org/vocabularies/meta#version")
	protected String version;

	@PropertyTerm("http://schema.org/description")
	protected String description;

	@PropertyTerm("http://raml.org/vocabularies/meta#raml")
	protected Raml raml;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#nodeMappings")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,NodeMapping>nodeMappings;
	
	
	@PropertyTerm("http://raml.org/vocabularies/meta#vocabularies")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,External>vocabularies;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#external")
	@Hash("http://raml.org/vocabularies/meta#name")
	protected Map<String,External>external;
}
