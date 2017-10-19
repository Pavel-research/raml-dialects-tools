package org.raml.dialects.toplevel.model;

import java.util.Map;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Hash;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#ModuleDeclaration")
public class ModuleDeclaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#declares")
	@Hash("http://raml.org/vocabularies/meta#name")
	Map<String, Declaration> declares;

	public Map<String, Declaration> getDeclares() {
		return declares;
	}

	public void setDeclares(Map<String, Declaration> declares) {
		this.declares = declares;
	}
}
