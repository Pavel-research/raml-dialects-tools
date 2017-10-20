package org.raml.dialects;

import java.io.Reader;
import java.net.URI;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.dialects.core.DialectRegistry;
import org.raml.dialects.core.IDataAdapter;
import org.raml.dialects.core.JSONOutput;
import org.raml.dialects.core.annotations.DomainRootElement;
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
	
	public String toStringRepresentation(JSONObject result,Class<?>target){
		String prettyJSONString = result.toString(4);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>)YAMLAdapter.yaml.load(prettyJSONString);
		//now we need to insert header
		String dump = YAMLAdapter.yaml.dump(map);
		DomainRootElement annotation = target.getAnnotation(DomainRootElement.class);
		if (annotation!=null){
			dump=DialectRegistry.header(annotation,target)+System.lineSeparator()+dump;
		}
		return dump;		
	}
}
