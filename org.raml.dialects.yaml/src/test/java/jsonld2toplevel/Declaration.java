package jsonld2toplevel;

import org.raml.dialects.core.annotations.CanBeValue;
import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.PropertyTerm;
import org.raml.dialects.core.annotations.Reference;

@ClassTerm("http://raml.org/vocabularies/meta#Declaration")
public class Declaration {

	@PropertyTerm("http://raml.org/vocabularies/meta#declaredNode")
	@Reference
	@CanBeValue
	protected NodeMappingTest declaredNode;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#name")
	protected String name;

	public NodeMappingTest getDeclaredNode() {
		return declaredNode;
	}

	public void setDeclaredNode(NodeMappingTest declaredNode) {
		this.declaredNode = declaredNode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
