package org.raml.vocabularies;

import java.util.Collection;
import java.util.Collections;

public enum Builtins implements DataType{
	BOOLEAN,INTEGER,NUMBER,URI,DATETIME,STRING,OBJECT,ANY;

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}
	
	private Builtins superType;

	@Override
	public boolean isSubTypeOf(DataType d) {		
		return this.superType==d;
	}
	
	static {
		INTEGER.superType=NUMBER;
		URI.superType=STRING;
		DATETIME.superType=STRING;
	}

	@Override
	public Collection<? extends DataType> getSuperTypes() {
		if (this.superType!=null) {
			return Collections.singleton(superType);
		}
		return Collections.emptyList();
	}
}

