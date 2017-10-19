package org.raml.jsonld2toplevel;

public interface ICanResolveIds {

	String resolveToURI(String id);
	Object resolveToEntity(String id);
}
