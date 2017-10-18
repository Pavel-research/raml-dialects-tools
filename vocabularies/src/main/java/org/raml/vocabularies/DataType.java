package org.raml.vocabularies;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public interface DataType {

	String getName();
	
	public default Map<String,PropertyTerm> getKnownProperties(){
		return Collections.emptyMap();
	}
	
	public boolean isSubTypeOf(DataType d) ;
	
	public Collection<? extends DataType>getSuperTypes();
}
