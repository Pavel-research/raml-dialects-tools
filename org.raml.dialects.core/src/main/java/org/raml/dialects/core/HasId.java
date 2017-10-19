package org.raml.dialects.core;

/**
 * implement it if object knows its uri
 * @author Pavel Petrochenko
 *
 */
public interface HasId {

	String id();
	
	String shortName();
}
