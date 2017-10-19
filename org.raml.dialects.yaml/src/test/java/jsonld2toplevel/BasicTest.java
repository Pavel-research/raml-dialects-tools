package jsonld2toplevel;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.dialects.core.AMFJSONLD;
import org.raml.dialects.core.DialectRegistry;
import org.raml.dialects.toplevel.model.YAMLAdapter;
import org.skyscreamer.jsonassert.JSONAssert;
import org.yaml.snakeyaml.Yaml;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	@Test
	public void test() {
		DialectTest readDocument = new AMFJSONLD()
				.readDocument(BasicTest.class.getResourceAsStream("/validation_dialect.json"), DialectTest.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation_dialect.json"))), true);

	}

	@Test
	public void test2() {
		DialectTest readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation.yaml"), DialectTest.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation2.json"))), true);
	}

	@Test
	public void test21() {
		DialectTest readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation2.yaml"),
				DialectTest.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation2.json"))), true);
	}

	@Test
	public void test3() {
		DialectTest readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation.yaml"), DialectTest.class);
		String yaml = YAMLAdapter.toYAML(readDocument);
		Object v1=new Yaml().load(yaml);
		Object v2=new Yaml().load(BasicTest.class.getResourceAsStream("/gold.yaml"));
		assertEquals(v1, v2);
	}
	
	@Test
	public void test4() {
		DialectTest readDocument;
		readDocument = DialectRegistry.getDefault().parse(BasicTest.class.getResource("/validation3.yaml"), DialectTest.class);
		String classTerm = readDocument.getRaml().getDocument().getEncodes().getClassTerm();
		assertEquals(classTerm,"http://raml.org/vocabularies/amf-validation#Profile");
	}
}