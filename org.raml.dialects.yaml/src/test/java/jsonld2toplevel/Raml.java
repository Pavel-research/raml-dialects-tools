package jsonld2toplevel;

import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;

@ClassTerm("http://raml.org/vocabularies/meta#Document")
public class Raml {

	@PropertyTerm("http://raml.org/vocabularies/meta#module")
	protected ModuleDeclaration module;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#document")
	protected DocumentDeclaration document;
	
	@PropertyTerm("http://raml.org/vocabularies/meta#fragments")
	protected FragmentsDeclaration fragments;

	public ModuleDeclaration getModule() {
		return module;
	}

	public void setModule(ModuleDeclaration module) {
		this.module = module;
	}

	public DocumentDeclaration getDocument() {
		return document;
	}

	public void setDocument(DocumentDeclaration document) {
		this.document = document;
	}

	public FragmentsDeclaration getFragments() {
		return fragments;
	}

	public void setFragments(FragmentsDeclaration fragments) {
		this.fragments = fragments;
	}
}
