package org.raml.dialects.core;

import java.io.InputStream;
import java.io.Reader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.raml.dialecs.core.model.JSONContext;
import org.raml.dialecs.core.model.JSONLDContext;
import org.raml.dialecs.core.model.JSONLDSerializationContext;
import org.raml.dialecs.core.model.JSONSerializationContext;
import org.raml.dialecs.core.model.NodeModel;
import org.raml.dialecs.core.model.NodeRegistry;

public final class AMFJSONLD {

	static final String ID = "@id";

	static final String TYPE = "@type";

	static final String VALUE = "@value";

	static final String ENCODES = "http://raml.org/vocabularies/document#encodes";

	static final String[] DOCUMENTTYPES = new String[] { "http://raml.org/vocabularies/document#Document",
			"http://raml.org/vocabularies/document#Fragment", "http://raml.org/vocabularies/document#Module",
			"http://raml.org/vocabularies/document#Unit" };

	private NodeRegistry nodeRegistry = new NodeRegistry();

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

	@SuppressWarnings("unchecked")
	private <T> T read(JSONArray array, Class<T> t) {
		JSONObject docobject = array.getJSONObject(0);
		JSONObject jsonObject = docobject.getJSONArray(ENCODES).getJSONObject(0);
		try {
			if (jsonObject.has(TYPE)){
				NodeModel model = nodeRegistry.getModel(jsonObject.getJSONArray(TYPE).getString(0));
				if (t.isAssignableFrom(model.getTargetClass())){
					t=(Class<T>) model.getTargetClass();
				}
			}
			T newInstance = t.newInstance();
			JSONLDContext ctx = new JSONLDContext();
			fill(jsonObject, newInstance, ctx);
			ctx.bind();
			return newInstance;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public JSONArray writeJSONLD(Object object, String id) {
		JSONArray res = new JSONArray();
		NodeModel register = nodeRegistry.register(object.getClass());
		JSONLDSerializationContext sc = new JSONLDSerializationContext();
		res.put(register.write(object, id + "#", sc));
		sc.bind();
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
		return register.writeJSON(object,new JSONSerializationContext(object, register));
	}

	public <T> T readFromJSON(JSONObject object, Class<T> t) {
		JSONContext ct = new JSONContext(object, nodeRegistry);
		T cast = t.cast(nodeRegistry.register(t).readFromJSON(object, 0, ct));
		ct.bind();
		return cast;
	}

	public String writeToJSONLDString(Object object, String id) {
		return writeJSONLD(object, id).toString();
	}

	private void fill(JSONObject jsonObject, Object newInstance, JSONLDContext ctx) {
		NodeModel register = nodeRegistry.register(newInstance.getClass());
		register.fill(jsonObject, newInstance, ctx);
	}

	public void register(Class<?> clazz) {
		nodeRegistry.register(clazz);
	}
}