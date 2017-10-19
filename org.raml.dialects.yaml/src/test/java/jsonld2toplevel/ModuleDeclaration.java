package jsonld2toplevel;

import java.util.Map;

import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.Hash;
import org.raml.dialects.core.annotations.PropertyTerm;

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
