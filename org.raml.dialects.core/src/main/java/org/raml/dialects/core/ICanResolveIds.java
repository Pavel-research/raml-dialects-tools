package org.raml.dialects.core;

public interface ICanResolveIds {

	String resolveToURI(String id);
	Object resolveToEntity(String id);
}
