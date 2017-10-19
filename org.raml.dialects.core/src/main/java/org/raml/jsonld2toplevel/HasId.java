package org.raml.jsonld2toplevel;

/**
 * implement it if object knows its uri
 * @author Pavel Petrochenko
 *
 */
public interface HasId {

	String id();
	
	String shortName();
}
