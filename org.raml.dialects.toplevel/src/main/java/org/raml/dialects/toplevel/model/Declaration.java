package org.raml.dialects.toplevel.model;

import org.raml.jsonld2toplevel.annotations.CanBeValue;
import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;
import org.raml.jsonld2toplevel.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#Declaration")
public class Declaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#declaredNode")
	@Reference
	@CanBeValue
	protected NodeMapping declaredNode;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;

	public NodeMapping getDeclaredNode() {
		return declaredNode;
	}

	public void setDeclaredNode(NodeMapping declaredNode) {
		this.declaredNode = declaredNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
