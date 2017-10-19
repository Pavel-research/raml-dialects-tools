package org.raml.vocabularies;

import java.util.Collections;
import java.util.List;

public class ExternalPropertyTerm implements PropertyTerm{

	public final String name;
	public final String iri;
	
	public ExternalPropertyTerm(String name, String iri) {
		super();
		this.name = name;
		this.iri = iri;
	}

	@Override
	public DataType getRange() {
		return Builtins.ANY;
	}

	@Override
	public LocalPropertyTerm getSuperProperty() {
		return null;
	}

	@Override
	public List<PropertyTerm> getSubProperties() {
		return Collections.emptyList();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iri == null) ? 0 : iri.hashCode());
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
		ExternalPropertyTerm other = (ExternalPropertyTerm) obj;
		if (iri == null) {
			if (other.iri != null)
				return false;
		} else if (!iri.equals(other.iri))
			return false;
		return true;
	}

}
