package org.raml.dialecs.core.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.raml.dialects.core.HasId;
import org.raml.dialects.core.annotations.BuiltinInstances;

public class JSONLDContext {

	protected static class Entry {
		protected final String id;
		protected final PropertyModel model;
		protected final Object target;

		public Entry(String id, PropertyModel model, Object target) {
			super();
			this.id = id;
			this.model = model;
			this.target = target;
		}
	}

	protected ArrayList<Entry> toBind = new ArrayList<Entry>();
	protected LinkedHashMap<String, Object> knownObjects = new LinkedHashMap<String,Object>();

	public Object getObject(String id){
		return this.knownObjects.get(id);
	}
	public void record(String id,Object object){
		this.knownObjects.put(id, object);
	}
	
	
	public void delay(String id, PropertyModel model, Object target){
		this.toBind.add(new Entry(id, model, target));
	}
	
	public void bind() {
		for (Entry e : toBind) {
			Object newValue = knownObjects.get(e.id);
			if (newValue==null){
				BuiltinInstances annotation = e.model.nodeType.getAnnotation(BuiltinInstances.class);
				if (annotation!=null){
					for (Class<?> c:annotation.value()){
						for (Field f:c.getDeclaredFields()){
							if (Modifier.isStatic(f.getModifiers())){
								if (e.model.nodeType.isAssignableFrom(f.getType())){
									try {
										HasId object = (HasId) f.get(null);
										newValue = resolveBuiltin(e, newValue, object);
									} catch (Exception e1) {
										throw new IllegalStateException(e1);
									}
								}
							}
						}
					}
				}
				
			}			
			e.model.append(e.target, newValue, null, null);
		}
	}
	protected Object resolveBuiltin(Entry e, Object newValue, HasId object) {
		if (object.id().equals(e.id)){
			newValue=object;											
		}
		return newValue;
	}
}
