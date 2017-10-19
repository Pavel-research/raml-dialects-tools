package org.raml.dialects.toplevel.model;

import org.raml.dialects.core.HasId;

public class Builtins extends NodeMapping implements HasId{

	private String shortName;
	private String id;

	private Builtins(String shortName, String id) { 
		this.shortName = shortName;
		this.name = shortName;
		this.id = id;
	}

	public static final Builtins STRING = new Builtins("string", "http://www.w3.org/2001/XMLSchema#string");
	public static final Builtins INTEGER = new Builtins("integer", "http://www.w3.org/2001/XMLSchema#integer");
	public static final Builtins FLOAT = new Builtins("float", "http://www.w3.org/2001/XMLSchema#float");
	public static final Builtins NUMBER = new Builtins("number", "http://www.w3.org/2001/XMLSchema#float");
	public static final Builtins BOOLEAN = new Builtins("boolean", "http://www.w3.org/2001/XMLSchema#boolean");
	public static final Builtins URI = new Builtins("boolean", "http://www.w3.org/2001/XMLSchema#anyURI");
	public static final Builtins ANY = new Builtins("any", "http://www.w3.org/2001/XMLSchema#anytype");

	@Override
	public String id() {
		return id;
	}
	
	@Override
	public String toString() {
		return shortName;
	}

	@Override
	public String shortName() {
		return shortName;
	}
}
