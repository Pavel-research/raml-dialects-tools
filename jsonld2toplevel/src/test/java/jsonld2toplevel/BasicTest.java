package jsonld2toplevel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.jsonld2toplevel.AMFJSONLD;
import org.skyscreamer.jsonassert.JSONAssert;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	@Test
	public void test() {
		ValidationProfile readDocument = new AMFJSONLD().readDocument(BasicTest.class.getResourceAsStream("/test.json"),
				ValidationProfile.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_profile_example.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/test.json"))), false);
	}

	@Test
	public void test1() {
		ValidationProfile2 readDocument = new AMFJSONLD()
				.readDocument(BasicTest.class.getResourceAsStream("/test.json"), ValidationProfile2.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_profile_example.raml");

		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/test.json"))), false);
	}

	@Test
	public void test2() {
		ValidationProfile2 readDocument = new AMFJSONLD()
				.readDocument(BasicTest.class.getResourceAsStream("/test.json"), ValidationProfile2.class);
		JSONAssert.assertEquals(new AMFJSONLD().writeJSON(readDocument),
				new JSONObject(new JSONTokener(BasicTest.class.getResourceAsStream("/test1.json"))), false);
	}

	@Test
	public void test3() {
		ValidationProfile2 readDocument = new AMFJSONLD().readFromJSON(
				new JSONObject(new JSONTokener(BasicTest.class.getResourceAsStream("/test1.json"))),
				ValidationProfile2.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_profile_example.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/test.json"))), false);
	}
}
