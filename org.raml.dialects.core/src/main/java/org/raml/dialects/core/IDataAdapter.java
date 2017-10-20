package org.raml.dialects.core;

import java.io.Reader;
import java.net.URI;

import org.json.JSONObject;

public interface IDataAdapter {

	JSONOutput adaptToJson(Reader reader,URI location, Class<?> target);
	
	String toStringRepresentation(JSONObject result,Class<?>target);
}
