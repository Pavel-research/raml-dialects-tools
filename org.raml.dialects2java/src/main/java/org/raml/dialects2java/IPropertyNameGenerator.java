package org.raml.dialects2java;

import org.raml.dialects.toplevel.model.PropertyMapping;

public interface IPropertyNameGenerator {
	public String name(PropertyMapping p);
}
