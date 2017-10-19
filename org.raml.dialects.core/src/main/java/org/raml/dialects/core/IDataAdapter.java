package org.raml.dialects.core;

import java.io.Reader;
import java.net.URI;

public interface IDataAdapter {

	JSONOutput adaptToJson(Reader reader,URI location, Class<?> target);

}
