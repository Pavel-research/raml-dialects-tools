package org.raml.jsonld2toplevel;

import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.raml.jsonld2toplevel.model.NodeModel;
import org.raml.jsonld2toplevel.model.NodeRegistry;
import org.raml.jsonld2toplevel.model.PropertyModel;

public final class AMFJSONLD {

	static final String ID = "@id";

	static final String TYPE = "@type";

	static final String VALUE = "@value";

	static final String ENCODES = "http://raml.org/vocabularies/document#encodes";

	static final String[] DOCUMENTTYPES = new String[] { "http://raml.org/vocabularies/document#Document",
			"http://raml.org/vocabularies/document#Fragment", "http://raml.org/vocabularies/document#Module",
			"http://raml.org/vocabularies/document#Unit" };

	private NodeRegistry nodeRegistry = new NodeRegistry();

	private HashSet<String> skip = new HashSet<String>();

	{
		skip.add(TYPE);
		skip.add(ID);
	}

	public <T> T readDocument(InputStream stream, Class<T> target) {
		JSONArray array = new JSONArray(new JSONTokener(stream));
		return readDocument(array, target);
	}

	public <T> T readDocument(Reader stream, Class<T> target) {
		JSONArray array = new JSONArray(new JSONTokener(stream));
		return readDocument(array, target);
	}

	public <T> T readDocument(JSONArray array, Class<T> target) {
		nodeRegistry.register(target);
		return read(array, target);
	}

	private <T> T read(JSONArray array, Class<T> t) {
		JSONObject docobject = array.getJSONObject(0);
		JSONObject jsonObject = docobject.getJSONArray(ENCODES).getJSONObject(0);
		try {
			T newInstance = t.newInstance();
			fill(jsonObject, newInstance, new LinkedHashMap<String, Object>());
			return newInstance;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public JSONArray writeJSONLD(Object object, String id) {
		JSONArray res = new JSONArray();
		NodeModel register = nodeRegistry.register(object.getClass());
		res.put(register.write(object, id + "#"));
		JSONArray tp = new JSONArray();
		JSONObject d0 = new JSONObject();
		d0.put(AMFJSONLD.ENCODES, res);
		d0.put(AMFJSONLD.ID, id);
		JSONArray vl = new JSONArray();
		for (String type : DOCUMENTTYPES) {
			vl.put(type);
		}
		d0.put(AMFJSONLD.TYPE, vl);
		tp.put(d0);
		return tp;
	}

	public JSONObject writeJSON(Object object) {
		NodeModel register = nodeRegistry.register(object.getClass());
		return register.writeJSON(object);
	}

	public <T> T readFromJSON(JSONObject object, Class<T> t) {
		return t.cast(nodeRegistry.register(t).readFromJSON(object));
	}

	public String writeToJSONLDString(Object object, String id) {
		return writeJSONLD(object, id).toString();
	}

	private void fill(JSONObject jsonObject, Object newInstance, HashMap<String, Object> knownObjects) {
		NodeModel register = nodeRegistry.register(newInstance.getClass());
		LinkedHashMap<String, PropertyModel> mappings = register.getMappings();
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
					propertyModel.append(newInstance, obj.get(VALUE), obj,null);
				} else if (obj.has(TYPE)) {
					String tp = obj.getJSONArray(TYPE).getString(0);
					String id = obj.getString(ID);
					NodeModel model = nodeRegistry.getModel(tp);
					Object propObject = knownObjects.get(id);
					if (propObject == null) {
						propObject = model.newInstance();
						knownObjects.put(id, propObject);
					}
					fill(obj, propObject, knownObjects);
					propertyModel.append(newInstance, propObject, obj,null);
					if (model == null) {
						throw new IllegalStateException("Type " + tp + " is not known to the system");
					}
				} else {
					throw new IllegalStateException("It is expected that values here should have @value  or @type");
				}
			}
		}
	}
}