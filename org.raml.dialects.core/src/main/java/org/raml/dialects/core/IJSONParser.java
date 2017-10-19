package org.raml.dialects.core;

import java.net.URI;

public interface IJSONParser<T> {

	T parse(JSONOutput reader,URI location, Class<? extends T> target);
	

}
