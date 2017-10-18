package org.raml.jsonld2toplevel.model;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.jsonld2toplevel.annotations.AlsoMappedTo;
import org.raml.jsonld2toplevel.annotations.ClassTerm;
import org.raml.jsonld2toplevel.annotations.Hash;
import org.raml.jsonld2toplevel.annotations.Mandatory;
import org.raml.jsonld2toplevel.annotations.DialectPropertyName;
import org.raml.jsonld2toplevel.annotations.PropertyTerm;
import org.raml.jsonld2toplevel.annotations.Reference;

public final class NodeRegistry {

	protected LinkedHashMap<String, NodeModel> models = new LinkedHashMap<String, NodeModel>();

	protected static HashSet<Class<?>> buildins = new HashSet<Class<?>>();

	static {
		buildins.add(String.class);
		buildins.add(Boolean.class);
		buildins.add(int.class);
		buildins.add(float.class);
		buildins.add(boolean.class);
		buildins.add(double.class);
		buildins.add(long.class);
		buildins.add(short.class);
		buildins.add(char.class);
		buildins.add(Integer.class);
		buildins.add(Float.class);
		buildins.add(Double.class);
		buildins.add(Long.class);
		buildins.add(Short.class);
		buildins.add(Character.class);
	}

	public NodeModel register(Class<?> clazz) {
		if (buildins.contains(clazz)) {
			return null;
		}
		if (Collection.class.isAssignableFrom(clazz)) {
			return null;
		}
		if (Map.class.isAssignableFrom(clazz)) {
			return null;
		}
		AlsoMappedTo also = clazz.getAnnotation(AlsoMappedTo.class);
		ArrayList<NodeModel> options = new ArrayList<NodeModel>();
		if (also != null) {
			for (Class<?> c : also.value()) {
				NodeModel option = register(c);
				options.add(option);
			}
		}
		ClassTerm annotation = clazz.getAnnotation(ClassTerm.class);
		if (annotation != null) {
			String term = annotation.value();
			if (models.containsKey(term)) {
				return models.get(term);
			} else {
				NodeModel mdl = new NodeModel(clazz, term);
				mdl.alternatives = options;
				models.put(term, mdl);
				process(mdl, clazz);
				return mdl;
			}
		}
		if (!options.isEmpty()) {
			throw new IllegalArgumentException(
					"Registered class " + clazz.getName() + " should have ClassTerm annotation");
		}
		return null;
	}

	private void process(NodeModel mdl, Class<?> clazz) {
		HashMap<String, PropertyModel> methodBindings = new HashMap<String, PropertyModel>();
		Method[] methods = clazz.getMethods();
		for (Method m : methods) {
			PropertyTerm annotation = m.getAnnotation(PropertyTerm.class);

			if (annotation != null) {
				String term = annotation.value();
				PropertyModel ts = mdl.mappings.get(term);
				if (ts == null) {
					String name = propName(m);
					ts = new PropertyModel(term, name, this);
					mdl.mappings.put(term, ts);
					methodBindings.put(name, ts);
				}
				propUpdate(m, ts);
			}
		}
		for (Method m : methods) {
			String name = propName(m);
			PropertyModel ts = methodBindings.get(name);

			if (ts != null) {
				propUpdate(m, ts);
			}
		}
		handleFieldBindings(mdl, clazz);
		for (PropertyModel m : mdl.mappings.values()) {
			mdl.dialectPropertyMappings.put(m.dialectName, m);
			register(m.nodeType);
		}
	}

	private void propUpdate(Method m, PropertyModel ts) {
		DialectPropertyName pName = m.getAnnotation(DialectPropertyName.class);
		if (pName != null) {
			ts.setDialectPropertyName(pName.value());
		}
		String name = m.getName();
		if (m.getParameterTypes().length == 0) {
			ts.getMethod = m;
			Type returnType = ts.getMethod.getGenericReturnType();
			proceedPropertyType(ts, returnType, m);
		} else if (name.startsWith("set") && m.getParameterTypes().length == 1) {
			ts.setMethod = m;
		}
	}

	private String propName(Method m) {
		String name = m.getName();
		if (name.startsWith("set") || name.startsWith("get")) {
			name = name.substring(3);
		} else if (name.startsWith("is")) {
			name = name.substring(2);
		}
		return name;
	}

	private void handleFieldBindings(NodeModel mdl, Class<?> clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			PropertyTerm annotation = f.getAnnotation(PropertyTerm.class);
			if (annotation != null) {
				String term = annotation.value();
				if (annotation != null) {
					f.setAccessible(true);
					PropertyModel ts = mdl.mappings.get(term);
					if (ts == null) {
						String fName = f.getName();
						DialectPropertyName pName = f.getAnnotation(DialectPropertyName.class);
						if (pName != null) {
							fName = pName.value();
						}
						ts = new PropertyModel(term, fName, this);
						mdl.mappings.put(term, ts);
					}
					ts.field = f;
					proceedPropertyType(ts, f.getGenericType(), f);
				}
			}
		}
		Class<?> superclass = clazz.getSuperclass();
		if (superclass != null) {
			handleFieldBindings(mdl, clazz.getSuperclass());
		}
	}

	private void proceedPropertyType(PropertyModel ts, Type returnType, AnnotatedElement memb) {
		Hash annotation = memb.getAnnotation(Hash.class);
		if (annotation != null) {
			ts.hash = annotation.value();
		}
		if (memb.getAnnotation(Mandatory.class) != null) {
			ts.required = true;
		}
		if (memb.getAnnotation(Reference.class) != null) {
			ts.reference = true;
		}
		if (returnType instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) returnType;
			Class<?> cl = (Class<?>) pt.getRawType();
			if (Collection.class.isAssignableFrom(cl)) {
				ts.allowMultiple = true;
			}
			if (Map.class.isAssignableFrom(cl)) {
				ts.asMap = true;
			}
			Type[] actualTypeArguments = pt.getActualTypeArguments();
			for (Type t : actualTypeArguments) {
				if (t instanceof Class<?>) {
					process(ts, t);
				}
			}
		} else if (returnType instanceof Class<?>) {
			Class<?> clazz = (Class<?>) returnType;
			register(clazz);
			ts.nodeType = clazz;
		} else {
			throw new IllegalStateException("Only classes, collections and maps are supported");
		}
	}

	private void process(PropertyModel ts, Type t) {
		Class<?> clazz = (Class<?>) t;
		ts.nodeType = clazz;
		register(clazz);
	}

	public NodeModel getModel(String tp) {
		return models.get(tp);
	}
}