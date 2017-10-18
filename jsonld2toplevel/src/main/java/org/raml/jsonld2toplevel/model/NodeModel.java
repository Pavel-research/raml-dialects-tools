package org.raml.jsonld2toplevel.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public final class NodeModel {

	private String term;

	public NodeModel(Class<?> clazz, String term) {
		this.targetClass = clazz;
		this.term = term;
	}

	protected final Class<?> targetClass;

	protected final LinkedHashMap<String, PropertyModel> mappings = new LinkedHashMap<String, PropertyModel>();

	protected final LinkedHashMap<String, PropertyModel> dialectPropertyMappings = new LinkedHashMap<String, PropertyModel>();

	protected ArrayList<NodeModel> alternatives = new ArrayList<NodeModel>();

	public LinkedHashMap<String, PropertyModel> getMappings() {
		return mappings;
	}

	public Object newInstance() {
		try {
			return targetClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public JSONObject write(Object value, String id) {
		JSONObject obj = new JSONObject();
		JSONArray tpArray = new JSONArray();
		tpArray.put(term);
		obj.put("@id", id);
		obj.put("@type", tpArray);
		for (PropertyModel m : this.mappings.values()) {
			m.writeToJSONLD(obj, value, id);
		}
		return obj;
	}

	public JSONObject writeJSON(Object object) {
		JSONObject target = new JSONObject();
		for (PropertyModel m : this.mappings.values()) {
			m.writeToJSON(object, target);
		}
		return target;
	}

	public Map<String, PropertyModel> getDialectPropertyMappings() {
		return dialectPropertyMappings;
	}

	public Object fill(JSONObject object, Object newInstance, LinkedHashMap<String, Object> idMap) {
		Map<String, PropertyModel> dialectPropertyMappings = getDialectPropertyMappings();
		for (String s : dialectPropertyMappings.keySet()) {
			if (object.has(s)) {
				Object value = object.get(s);
				PropertyModel propertyModel = dialectPropertyMappings.get(s);
				propertyModel.readFromJSON(newInstance, value, object);
			}
		}
		return newInstance;
	}

	public Object readFromJSON(JSONObject object) {
		try {
			if (!this.alternatives.isEmpty()) {
				NodeModel alrernative = null;
				for (NodeModel m : alternatives) {
					if (isOk(object, m)) {
						alrernative = m;
						break;
					}
				}
				if (alrernative != null) {
					return alrernative.readFromJSON(object);
				}
			}
			Object newInstance = targetClass.newInstance();
			fill(object, newInstance, new LinkedHashMap<String, Object>());
			return newInstance;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	private boolean isOk(JSONObject object, NodeModel m) {
		boolean isOk = true;
		for (PropertyModel model : m.dialectPropertyMappings.values()) {
			if (model.required) {
				if (!object.has(model.dialectName)) {
					isOk = false;
					break;
				}
			}
		}
		return isOk;
	}
}