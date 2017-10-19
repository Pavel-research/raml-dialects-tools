package org.raml.dialecs.core.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class JSONSerializationContext {

	private static final String URI = "http://raml.org/vocabularies/meta#uri";

	private static final String NAME = "http://raml.org/vocabularies/meta#name";

	private static final String EXTERNAL = "http://raml.org/vocabularies/meta#external";

	protected Object root;

	protected NodeModel rootModel;

	protected HashMap<String, String> prefixes = new HashMap<String, String>();

	@SuppressWarnings("unchecked")
	public JSONSerializationContext(Object root, NodeModel rootModel) {
		super();
		this.root = root;
		this.rootModel = rootModel;
		for (PropertyModel m : rootModel.mappings.values()) {
			if (m.propertyTerm.equals(EXTERNAL)) {
				Map<String, Object> externals = (Map<String, Object>) m.getValue(root);
				for (Object o : externals.values()) {
					NodeModel register = rootModel.registry.register(o.getClass());
					LinkedHashMap<String, PropertyModel> mappings = register.getMappings();
					String uri = null;
					String name = null;
					for (PropertyModel m1 : mappings.values()) {
						if (m1.propertyTerm.equals(NAME)) {
							name = (String) m1.getValue(o);
						}
						if (m1.propertyTerm.equals(URI)) {
							uri = (String) m1.getValue(o);
						}
					}
					prefixes.put(uri, name);
				}
			}
		}
	}

	public Object resolve(String value) {
		for (String s : prefixes.keySet()) {
			if (value.startsWith(s)) {
				String end = value.substring(s.length());
				return prefixes.get(s) + "." + end;
			}
		}
		return value;
	}

}
