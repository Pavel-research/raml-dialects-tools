package org.raml.jsonld2toplevel;

import java.io.Reader;
import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class DefaultParser implements IParser<Object>,IJSONParser<Object>{

	@Override
	public Class<Object> result() {
		return Object.class;
	}

	@Override
	public Object parse(Reader reader, URI location, Class<? extends Object> target) {
		JSONTokener jsonTokener = new JSONTokener(reader);
		Object nextValue = jsonTokener.nextValue();
		if (nextValue instanceof JSONObject){
			return new AMFJSONLD().readFromJSON((JSONObject)nextValue, target);
		}
		else if (nextValue instanceof JSONArray){
			return new AMFJSONLD().readDocument((JSONArray)nextValue, target);
		}
		throw new IllegalStateException("json array or json object is expected");
	}

	@Override
	public Object parse(JSONOutput reader, URI location, Class<? extends Object> target) {
		if (reader.array!=null){
			return new AMFJSONLD().readDocument(reader.array, target);				
		}
		if (reader.object!=null){
			return new AMFJSONLD().readFromJSON(reader.object, target);	
		}
		throw new IllegalStateException("json array or json object is expected");
	}

	@Override
	public String[] supportedExtensions() {
		return new String[]{"json","jsonld"};
	}

}
