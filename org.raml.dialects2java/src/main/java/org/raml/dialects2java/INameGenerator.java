package org.raml.dialects2java;

import org.raml.dialects.toplevel.model.NodeMapping;

public interface INameGenerator {

	String fullyQualifiedName(NodeMapping t);

}
