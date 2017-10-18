# Vocabularies

Simple Java library for reading Antonio RAML Vocabularies. 

Usage: `new Vocabulary(url)`

```java
		try {
			URL in = BasicTests.class.getResource("/raml-http.raml");
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
```		

Known limitations: Cyclic vocabulary dependencies are not supported.
