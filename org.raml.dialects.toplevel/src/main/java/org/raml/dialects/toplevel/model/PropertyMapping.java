package org.raml.dialects.toplevel.model;

import java.util.ArrayList;
import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.DialectPropertyName;
import org.raml.jsonld2toplevel.annotations.NeedsResolving;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#PropertyMapping")
public class PropertyMapping {

	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;

	@PropertyTerm("http://raml.org/vocabularies/meta#propertyTerm")
	@NeedsResolving
	protected String propertyTerm;

	@PropertyTerm("http://raml.org/vocabularies/meta#hash")
	@NeedsResolving
	protected String hash;

	@PropertyTerm("http://raml.org/vocabularies/meta#pattern")
	protected String pattern;

	@PropertyTerm("http://raml.org/vocabularies/meta#mandatory")
	protected boolean mandatory;

	@PropertyTerm("http://raml.org/vocabularies/meta#allowMultiple")
	protected boolean allowMultiple;

	@PropertyTerm("http://raml.org/vocabularies/meta#asMap")
	protected boolean asMap;

	@PropertyTerm("http://raml.org/vocabularies/meta#range")
	protected ArrayList<String> range;

	@PropertyTerm("http://raml.org/vocabularies/meta#enum")
	@DialectPropertyName("enum")
	protected ArrayList<String> enumValues;
}