package jsonld2toplevel;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.dialects.toplevel.model.Dialect;
import org.raml.jsonld2toplevel.AMFJSONLD;
import org.skyscreamer.jsonassert.JSONAssert;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	@Test
	public void test() {
		Dialect readDocument = new AMFJSONLD().readDocument(BasicTest.class.getResourceAsStream("/validation_dialect.json"),
				Dialect.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation_dialect.json"))), false);
		
	}

	
}
