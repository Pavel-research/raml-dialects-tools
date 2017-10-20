package org.raml.vocabularies;

import java.util.Collection;
import java.util.Collections;

public class ExternalClass implements DataType{

	private String name;
	private String uri;
	
	public ExternalClass(String name,String uri) {
		this.name=name;
		this.uri=uri;
	}
	
	@Override
	public String getName() {
		return name;
	}
	public String getUri(){
		return this.uri;
	}

	@Override
	public boolean isSubTypeOf(DataType d) {
		return false;
	}

	@Override
	public Collection<? extends DataType> getSuperTypes() {
		return Collections.emptyList();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExternalClass other = (ExternalClass) obj;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
}
