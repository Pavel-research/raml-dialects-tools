package jsonld2toplevel;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.dialects.toplevel.model.YAMLAdapter;
import org.raml.jsonld2toplevel.AMFJSONLD;
import org.raml.jsonld2toplevel.DialectRegistry;
import org.skyscreamer.jsonassert.JSONAssert;
import org.yaml.snakeyaml.Yaml;

import junit.framework.Assert;
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
		Dialect readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation.yaml"), Dialect.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation2.json"))), true);
	}

	@Test
	public void test21() {
		Dialect readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation2.yaml"),
				Dialect.class);
		JSONArray write = new AMFJSONLD().writeJSONLD(readDocument,
				"file:/shared/src/test/resources/vocabularies/validation_dialect.raml");
		JSONAssert.assertEquals(write,
				new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/validation2.json"))), true);
	}

	@Test
	public void test3() {
		Dialect readDocument = YAMLAdapter.load(BasicTest.class.getResource("/validation.yaml"), Dialect.class);
		String yaml = YAMLAdapter.toYAML(readDocument);
		Object v1=new Yaml().load(yaml);
		Object v2=new Yaml().load(BasicTest.class.getResourceAsStream("/gold.yaml"));
		assertEquals(v1, v2);
	}
	
	@Test
	public void test4() {
		Dialect readDocument;
		readDocument = DialectRegistry.getDefault().parse(BasicTest.class.getResource("/validation3.yaml"), Dialect.class);
		String classTerm = readDocument.getRaml().getDocument().getEncodes().getClassTerm();
		System.out.println(classTerm);		
	}
}