package org.raml.jsonld2toplevel.model;

import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.raml.jsonld2toplevel.HasId;

public final class JSONContext extends JSONLDContext {

	private static final String USES = "uses";

	private static final String EXTERNAL = "external";

	protected LinkedHashMap<String, String> externals = new LinkedHashMap<String, String>();

	protected LinkedHashMap<String, Object> uses = new LinkedHashMap<String, Object>();

	protected NodeRegistry registry;

	public JSONContext(JSONObject object, NodeRegistry registry) {
		this.registry = registry;
		if (object.has(EXTERNAL)) {
			JSONObject external = object.getJSONObject(EXTERNAL);
			for (String s : external.keySet()) {
				externals.put(s, external.getString(s));
			}
		}
		if (object.has(USES)) {
			JSONObject external = object.getJSONObject(USES);
			for (String s : external.keySet()) {
				uses.put(s, external.get(s));
			}
		}
	}

	public String revolve(String string) {
		int indexOf = string.indexOf('.');
		if (indexOf != -1) {
			String namespace = string.substring(0, indexOf);
			if (externals.containsKey(namespace)) {
				return externals.get(namespace) + string.substring(indexOf + 1);
			}
			if (uses.containsKey(namespace)){
				Object object = uses.get(namespace);
			}
		}
		return string;
	}
	
	protected Object resolveBuiltin(Entry e, Object newValue, HasId object) {
		if (object.shortName().equals(e.id)){
			newValue=object;											
		}
		return newValue;
	}

}