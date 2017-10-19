package org.raml.jsonld2toplevel.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.raml.jsonld2toplevel.HasId;

public final class JSONLDSerializationContext {

	protected static class Entry {
		protected final Object model;
		protected final JSONObject target;

		public Entry(Object model, JSONObject target) {
			super();
			this.model = model;
			this.target = target;
		}
	}

	protected ArrayList<Entry> toBind = new ArrayList<Entry>();
	protected LinkedHashMap< Object,String> knownObjects = new LinkedHashMap<Object,String>();

	public Object getObject(String id){
		return this.knownObjects.get(id);
	}
	public void record(String id,Object object){
		this.knownObjects.put(object,id);
	}
	
	
	public void delay(Object model, JSONObject target){
		this.toBind.add(new Entry(model, target));
	}
	
	public void bind() {
		for (Entry e : toBind) {
			String value = knownObjects.get(e.model);
			if (value==null){
				if (e.model instanceof HasId){
					value=((HasId) e.model).id();
				}
			}
			e.target.put("@value", value);
		}
	}
}
