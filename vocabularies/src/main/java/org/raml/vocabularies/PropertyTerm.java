package org.raml.vocabularies;

import java.util.List;

public interface PropertyTerm {

	public DataType getRange();

	public PropertyTerm getSuperProperty();

	public List<PropertyTerm> getSubProperties();
}
