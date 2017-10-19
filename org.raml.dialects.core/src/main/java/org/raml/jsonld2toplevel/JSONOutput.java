package org.raml.jsonld2toplevel;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONOutput {

	public final JSONObject object;
	public final JSONArray array;
	
	public JSONOutput(JSONObject object, JSONArray array) {
		super();
		this.object = object;
		this.array = array;
	}
	
	@Override
	public String toString() {
		if (this.object!=null){
			return this.object.toString();
		}
		if (this.array!=null){
			return this.array.toString();
		}
		return "Wrong JSON";
	}
}
