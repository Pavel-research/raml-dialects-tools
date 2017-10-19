package org.raml.jsonld2toplevel;

import java.io.Reader;
import java.net.URI;

public interface IDataAdapter {

	JSONOutput adaptToJson(Reader reader,URI location, Class<?> target);

}
