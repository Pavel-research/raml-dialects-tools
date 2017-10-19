package org.raml.dialecs.core.model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.dialects.core.HasId;
import org.raml.dialects.core.annotations.ClassTerm;

public class PropertyModel {

	private static final String NAME = "http://schema.org/name";

	private static final String VALUE = "@value";

	private static final String ID = "@id";

	protected final String propertyTerm;

	private NodeRegistry registry;

	protected String dialectName;

	protected String hash;

	protected boolean allowMultiple;

	protected boolean asMap;

	protected Method getMethod;

	protected Method setMethod;

	protected Field field;

	protected Class<?> nodeType;

	protected boolean required;

	protected boolean reference;

	protected boolean resolve;

	protected boolean canBeValue;

	public PropertyModel(String propertyTerm, String dialectName, NodeRegistry reg) {
		super();
		this.propertyTerm = propertyTerm;
		this.registry = reg;
		this.dialectName = dialectName;
	}

	@SuppressWarnings("unchecked")
	public void append(Object target, Object newValue, JSONObject oo, String key) {
		try {
			newValue = convertIfNeeded(newValue);
			if (!allowMultiple && !asMap) {
				setValue(target, newValue);
			} else if (allowMultiple) {
				Collection<Object> object = (Collection<Object>) getValue(target);
				if (object != null) {
					object.add(newValue);
				} else {
					Class<?> type = newInstanceType(getType());

					Collection<Object> newInstance = (Collection<Object>) type.newInstance();
					setValue(target, newInstance);
					newInstance.add(newValue);
				}
			} else {
				Map<Object, Object> object = (Map<Object, Object>) getValue(target);
				if (object != null) {
					object.put(hashValue(oo, key), newValue);
				} else {
					Class<?> type = newInstanceType(getType());

					Map<Object, Object> newInstance = (Map<Object, Object>) type.newInstance();
					setValue(target, newInstance);
					newInstance.put(hashValue(oo, key), newValue);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}

	}

	private Object convertIfNeeded(Object newValue) {
		if (!nodeType.isInstance(newValue)) {
			if (nodeType == String.class) {
				newValue = newValue.toString();
			}
		}
		return newValue;
	}

	private Object hashValue(JSONObject oo, String key) {
		if (key != null) {
			return key;
		}
		return oo.getJSONArray(hash).getJSONObject(0).get(VALUE);
	}

	private Class<?> getType() {
		if (this.field != null) {
			return this.field.getType();
		}
		return this.getMethod.getReturnType();
	}

	Object getValue(Object source) {
		try {
			if (this.field != null) {
				return this.field.get(source);
			}
			return this.getMethod.invoke(source);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	void setValue(Object target, Object value) {
		try {
			if (this.field != null) {

				this.field.set(target, value);
			} else
				this.setMethod.invoke(target, value);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void writeToJSONLD(JSONObject obj, Object source, String id, JSONLDSerializationContext sc) {
		JSONArray produce = produce(source, id, sc);
		if (produce != null) {
			obj.put(this.propertyTerm, produce);
		}
	}

	private JSONArray produce(Object source, String id, JSONLDSerializationContext sc) {
		Object value = getValue(source);

		if (value != null) {
			if (value.equals(Boolean.FALSE)) {
				return null;
			}
			JSONArray res = new JSONArray();
			if (asMap && value instanceof Map<?, ?>) {
				Map<?, ?> map = (Map<?, ?>) value;
				if (!recordCollection(id, res, map.values(), sc)) {
					return null;
				}
			} else if (value instanceof Collection) {
				Collection<?> collection = (Collection<?>) value;
				if (!recordCollection(id, res, collection, sc)) {
					return null;
				}
			} else {
				res.put(proceed(value, id, sc));
			}
			return res;
		}
		return null;
	}

	private boolean recordCollection(String id, JSONArray res, Collection<?> collection,
			JSONLDSerializationContext sc) {
		if (collection.isEmpty()) {
			return false;
		}

		for (Object o : collection) {
			res.put(proceed(o, id, sc));
		}
		return true;
	}

	private JSONObject proceed(Object value, String id, JSONLDSerializationContext sc) {

		if (isBuiltin(value)) {
			JSONObject object = new JSONObject();
			if (this.reference) {

				object.put(ID, value);
			} else {
				object.put(VALUE, value);
			}
			return object;
		} else {
			if (this.reference) {
				JSONObject object = new JSONObject();
				sc.delay(value, object);
				return object;
			}
			NodeModel register = registry.register(value.getClass());
			String hp = NAME;

			if (this.hash != null) {
				hp = this.hash;
			}
			String localId = this.dialectName;
			if (this.allowMultiple || this.asMap) {
				PropertyModel nm = register.getMappings().get(hp);
				if (nm != null) {
					localId = "" + nm.getValue(value);
				}
			}
			if (id.endsWith("#")) {
				id = id + localId;
			} else {
				id = id + "/" + localId;
			}
			return register.write(value, id, sc);
		}
	}

	private Class<?> newInstanceType(Class<?> type) {
		if (type == Set.class) {
			return LinkedHashSet.class;
		}
		if (type == Map.class) {
			return LinkedHashMap.class;
		}
		if (type == Collection.class) {
			return LinkedHashSet.class;
		}
		if (type == List.class) {
			return ArrayList.class;
		}
		return type;
	}

	public void setDialectPropertyName(String pName) {
		this.dialectName = pName;
	}

	public void writeToJSON(Object obj, JSONObject target, JSONSerializationContext jsonSerializationContext) {
		Object value = getValue(obj);
		if (value instanceof Collection<?>) {
			Collection<?> cm = (Collection<?>) value;
			if (!cm.isEmpty()) {
				if (cm.size() == 1) {
					target.put(this.dialectName, obj2json(cm.iterator().next(), jsonSerializationContext));
					return;
				}
				JSONArray arr = new JSONArray();
				for (Object o : cm) {
					arr.put(obj2json(o, jsonSerializationContext));
				}
				target.put(this.dialectName, arr);
			}
		} else if (value instanceof Map<?, ?>) {
			Map<?, ?> cm = (Map<?, ?>) value;
			if (!cm.isEmpty()) {
				JSONObject map = new JSONObject();
				for (Object key : cm.keySet()) {
					Object val = cm.get(key);
					Object obj2json = obj2json(val, jsonSerializationContext);
					map.put(key.toString(), obj2json);
					removeHash(obj2json);
				}
				target.put(this.dialectName, map);
			}
		} else {
			if (value != null) {
				if (value.equals(Boolean.FALSE)) {
					return;
				}
				target.put(this.dialectName, obj2json(value, jsonSerializationContext));
			}
		}
	}

	private Object removeHash(Object obj2json) {
		return ((JSONObject) obj2json).remove(registry.register(this.nodeType).getMappings().get(hash).dialectName);
	}

	private Object obj2json(Object val, JSONSerializationContext ct) {
		if (this.resolve&&val instanceof String) {
			String st=(String) val;
			val=ct.resolve(st);
		}
		if (isBuiltin(val)) {
			return val;
		}

		if (this.reference) {
			if (val instanceof HasId) {
				return ((HasId) val).shortName();
			}
			NodeModel register = this.registry.register(this.nodeType);
			if (register.valueProperty != null) {
				return register.valueProperty.getValue(val);
			}
			return val.toString();
		}
		return registry.register(val.getClass()).writeJSON(val, ct);
	}

	private boolean isBuiltin(Object value) {
		return value instanceof String || value instanceof Number || value instanceof Character
				|| value instanceof Boolean;
	}

	public void readFromJSON(Object newInstance, Object value, JSONObject original, JSONContext ct, int level) {
		if (this.resolve) {
			value = ct.revolve(value.toString());
		}
		if (isBuiltin(value)) {
			if (isObject() && this.reference) {
				ct.delay(value.toString(), this, newInstance);
			} else
				append(newInstance, value, original, null);
		} else if (value instanceof JSONArray) {
			JSONArray arr = (JSONArray) value;
			for (Object v : arr) {
				if (v instanceof JSONObject) {
					append(newInstance, parseJSONObject(v, ct, level), original, null);
				} else {
					if (isObject() && this.reference) {
						ct.delay(v.toString(), this, newInstance);
					} else
						append(newInstance, v, original, null);
				}
			}
		} else {
			JSONObject vObject = (JSONObject) value;
			if (this.asMap) {

				for (String key : vObject.keySet()) {
					Object object = vObject.get(key);
					if (object instanceof JSONObject) {
						Object parseJSONObject = parseJSONObject(object, ct, level);
						setHash(key, parseJSONObject);
						append(newInstance, parseJSONObject, (JSONObject) object, key);
						if (level == 0) {
							ct.record(key, parseJSONObject);
						}
					} else {
						if (isObject() && this.reference) {
							ct.delay(value.toString(), this, newInstance);
						} else {
							if (isObject()) {
								try {
									Object newobject = nodeType.newInstance();
									setHash(key, newobject);
									PropertyModel valueProperty = registry.register(nodeType).valueProperty;
									if (valueProperty != null && valueProperty.isObject() && valueProperty.reference) {
										ct.delay(object.toString(), valueProperty, newobject);
									} else
										valueProperty.append(newobject, object, null, key);
									object = newobject;
								} catch (Exception e) {
									throw new IllegalStateException(e);
								}
							}
							append(newInstance, object, null, key);
						}
					}
				}
			} else {
				append(newInstance, parseJSONObject(vObject, ct, level), original, null);
			}
		}
	}

	private void setHash(String key, Object parseJSONObject) {
		NodeModel register = registry.register(parseJSONObject.getClass());
		PropertyModel propertyModel = register.getMappings().get(hash);
		propertyModel.setValue(parseJSONObject, key);
	}

	private Object parseJSONObject(Object v, JSONContext ct, int level) {
		return registry.register(nodeType).readFromJSON((JSONObject) v, level + 1, ct);
	}

	public boolean isObject() {
		return this.nodeType.getAnnotation(ClassTerm.class) != null;
	}
}