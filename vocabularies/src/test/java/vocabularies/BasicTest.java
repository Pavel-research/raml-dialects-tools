package vocabularies;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import org.raml.vocabularies.ClassTerm;
import org.raml.vocabularies.DataType;
import org.raml.vocabularies.Vocabulary;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	public void test0() {
		try {
			URL in = BasicTest.class.getResource("/raml-http.raml");
			Vocabulary vocabulary = new Vocabulary(in);
			TestCase.assertEquals(vocabulary.getClassTerms().size(), 11);
			ClassTerm classTerm = vocabulary.getClassTerms().get("EndPoint");
			Collection<ClassTerm> superTypes = classTerm.getSuperTypes();
			TestCase.assertEquals(superTypes.size(), 1);
			TestCase.assertEquals(classTerm.getKnownProperties().keySet().toString(),
					"[source, schema-org.name, schema-org.description, path, hydra.supportedOperation]");
			DataType rng = classTerm.getProperty("path").getRange();
			String nm=rng.getName();
			TestCase.assertEquals(nm, "string");
			TestCase.assertEquals(classTerm.isSubTypeOf(vocabulary.resolveClass("raml-doc.DomainElement")),true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}