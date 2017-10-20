package org.raml.dialects2java;

import org.junit.Test;
import org.raml.dialects.core.DialectRegistry;

public class BasicTest extends CompilerTestCase {

	@Test
	public void test() {
		Class<?> compileAndLoadClass = compileAndLoadClass("validation3.yaml","profileNode");
		Object parse = DialectRegistry.getDefault().parse(BasicTest.class.getResource("/test.json"),compileAndLoadClass);
		try {
			Object invoke = parse.getClass().getMethod("getVersion").invoke(parse);
			assertEquals(invoke, "0.1-SNAPSHOT");
		} catch (Exception e) {
			fail();
		}
	}


}