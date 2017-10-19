package jsonld2toplevel;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.dialects.toplevel.model.Dialect;
import org.raml.jsonld2toplevel.AMFJSONLD;
import org.skyscreamer.jsonassert.JSONAssert;
import org.yaml.snakeyaml.Yaml;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	@Test
	public void test() {
		Dialect readDocument = new AMFJSONLD()
				.readDocument(BasicTest.class.getResourceAsStream("/validation_dialect.json"), Dialect.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation_dialect.json"))), true);

	}

	@Test
	public void test2() {
		Yaml yaml= new Yaml();
	    Object obj = yaml.load(BasicTest.class.getResourceAsStream("/validation.yaml"));
		Dialect readDocument = new AMFJSONLD()
				
				.readFromJSON((JSONObject)_convertToJson(obj), Dialect.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation2.json"))), true);

	}

	@SuppressWarnings("unchecked")
	private static Object _convertToJson(Object o) throws JSONException {
		if (o instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) o;

			JSONObject result = new JSONObject();

			for (Map.Entry<Object, Object> stringObjectEntry : map.entrySet()) {
				String key = stringObjectEntry.getKey().toString();

				result.put(key, _convertToJson(stringObjectEntry.getValue()));
			}

			return result;
		} else if (o instanceof ArrayList) {
			@SuppressWarnings("rawtypes")
			ArrayList arrayList = (ArrayList) o;
			JSONArray result = new JSONArray();

			for (Object arrayObject : arrayList) {
				result.put(_convertToJson(arrayObject));
			}

			return result;
		} else if (o instanceof String) {
			return o;
		} else if (o instanceof Boolean) {
			return o;
		} else {

			return o;
		}
	}
}