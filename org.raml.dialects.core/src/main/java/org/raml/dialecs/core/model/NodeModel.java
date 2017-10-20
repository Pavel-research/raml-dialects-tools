package org.raml.dialecs.core.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public final class NodeModel {

	private String term;

	protected NodeRegistry registry;

	public NodeModel(Class<?> clazz, String term,NodeRegistry registry) {
		this.targetClass = clazz;
		this.term = term;
		this.registry=registry;
	}
	static final String ID = "@id";

	static final String TYPE = "@type";

	static final String VALUE = "@value";

	static final String ENCODES = "http://raml.org/vocabularies/document#encodes";

	protected final Class<?> targetClass;

	protected final LinkedHashMap<String, PropertyModel> mappings = new LinkedHashMap<String, PropertyModel>();

	protected final LinkedHashMap<String, PropertyModel> dialectPropertyMappings = new LinkedHashMap<String, PropertyModel>();

	protected ArrayList<NodeModel> alternatives = new ArrayList<NodeModel>();
	
	protected PropertyModel valueProperty;

	public LinkedHashMap<String, PropertyModel> getMappings() {
		return mappings;
	}

	private HashSet<String> skip = new HashSet<String>();

	{
		skip.add(TYPE);
		skip.add(ID);
	}
	public void fill(JSONObject jsonObject, Object newInstance, JSONLDContext ctx) {
		LinkedHashMap<String, PropertyModel> mappings = getMappings();
		for (String prop : jsonObject.keySet()) {
			if (skip.contains(prop)) {
				continue;
			}
			PropertyModel propertyModel = mappings.get(prop);
			if (propertyModel == null) {
				throw new IllegalStateException("It is not clear how to map property term:" + prop
						+ " in the context of " + newInstance.getClass().getName());
			}
			JSONArray jsonArray = jsonObject.getJSONArray(prop);
			for (Object o : jsonArray) {
				JSONObject obj = (JSONObject) o;
				if (obj.has(VALUE)) {
					if (propertyModel.isObject()){
						ctx.delay(obj.getString(VALUE), propertyModel, newInstance);
					}
					else propertyModel.append(newInstance, obj.get(VALUE), obj, null);
				} else if (obj.has(TYPE)) {
					String tp = obj.getJSONArray(TYPE).getString(0);
					String id = obj.getString(ID);
					NodeModel model = registry.getModel(tp);
					Object propObject = ctx.getObject(id);
					if (propObject == null) {
						propObject = model.newInstance();
						ctx.record(id, propObject);
					}
					if (model == null) {
						throw new IllegalStateException("Type " + tp + " is not known to the system");
					}
					else {
						model.fill(obj, propObject, ctx);
					}
					propertyModel.append(newInstance, propObject, obj, null);
					
				} else {
					if (obj.has(ID)) {
						propertyModel.append(newInstance, obj.get(ID), obj, null);
					} else
						throw new IllegalStateException("It is expected that values here should have @value  or @type");
				}
			}
		}
	}
	
	private Object newInstance() {
		try {
			return targetClass.newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public JSONObject write(Object value, String id, JSONLDSerializationContext sc) {
		JSONObject obj = new JSONObject();
		JSONArray tpArray = new JSONArray();
		sc.record(id, value);
		tpArray.put(term);
		obj.put("@id", id);
		obj.put("@type", tpArray);
		for (PropertyModel m : this.mappings.values()) {
			m.writeToJSONLD(obj, value, id,sc);
		}
		return obj;
	}

	public JSONObject writeJSON(Object object, JSONSerializationContext jsonSerializationContext) {
		JSONObject target = new JSONObject();
		for (PropertyModel m : this.mappings.values()) {
			m.writeToJSON(object, target,jsonSerializationContext);
		}
		return target;
	}

	public Map<String, PropertyModel> getDialectPropertyMappings() {
		return dialectPropertyMappings;
	}

	public Object fill(JSONObject object, Object newInstance, JSONContext ct,int level) {
		Map<String, PropertyModel> dialectPropertyMappings = getDialectPropertyMappings();
		for (String s : dialectPropertyMappings.keySet()) {
			if (object.has(s)) {
				Object value = object.get(s);
				
				PropertyModel propertyModel = dialectPropertyMappings.get(s);
				propertyModel.readFromJSON(newInstance, value, object, ct,level);
			}
		}
		return newInstance;
	}

	public Object readFromJSON(JSONObject object, int level, JSONContext ct) {
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
					return alrernative.readFromJSON(object, level, ct);
				}
			}
			Object newInstance = targetClass.newInstance();
			fill(object, newInstance,  ct,level);
			return newInstance;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static boolean isOk(JSONObject object, NodeModel m) {
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

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public int fits(JSONObject v) {
		int count=0;
		for (String k:this.dialectPropertyMappings.keySet()){
			if (v.has(k)){
				count++;
			}
		}
		return count;
	}
}