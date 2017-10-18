package org.raml.jsonld2toplevel.model;

import java.util.LinkedHashMap;

import org.json.JSONObject;

public class Context {

	protected LinkedHashMap<String, String> externals = new LinkedHashMap<>();

	protected LinkedHashMap<String, Object> uses = new LinkedHashMap<>();

	protected NodeRegistry registry;

	public Context(JSONObject object, NodeRegistry registry) {
		this.registry = registry;
		if (object.has("external")) {
			JSONObject external = object.getJSONObject("external");
			for (String s : external.keySet()) {
				externals.put(s, external.getString(s));
			}
		}
		if (object.has("uses")) {
			JSONObject external = object.getJSONObject("uses");
			for (String s : external.keySet()) {
				externals.put(s, external.getString(s));
			}
		}
	}

	public String revolve(String string) {
		int indexOf = string.indexOf('.');
		if (indexOf!=-1){
			String namespace=string.substring(0, indexOf);
			if (externals.containsKey(namespace)){
				return externals.get(namespace)+string.substring(indexOf+1);
			}
		}
		return string;
	}
}