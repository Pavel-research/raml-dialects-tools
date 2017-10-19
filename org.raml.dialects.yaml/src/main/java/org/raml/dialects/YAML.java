package org.raml.dialects;

import java.io.Reader;
import java.net.URI;

import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.dialects.core.IDataAdapter;
import org.raml.dialects.core.JSONOutput;
import org.raml.dialects.toplevel.model.YAMLAdapter;
import org.yaml.snakeyaml.Yaml;

public class YAML implements IDataAdapter {

	@Override
	public JSONOutput adaptToJson(Reader reader, URI location, Class<?> target) {
		Yaml yaml = YAMLAdapter.createYAMLInstance(location);
		Object obj = yaml.load(reader);
		Object _convertToJson2 = new YAMLAdapter()._convertToJson(obj, true, location);
		if (_convertToJson2 instanceof JSONObject) {
			return new JSONOutput((JSONObject) _convertToJson2, null);
		}
		if (_convertToJson2 instanceof JSONArray) {
			return new JSONOutput(null, (JSONArray) _convertToJson2);
		}
		throw new IllegalStateException("sequence of map is expected");
	}
}
