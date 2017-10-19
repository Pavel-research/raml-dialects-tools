package org.raml.dialects2java;

import org.junit.Test;

public class BasicTest extends CompilerTestCase {

	@Test
	public void test() {
		Class<?> compileAndLoadClass = compileAndLoadClass("validation3.yaml","profileNode");
		System.out.println(compileAndLoadClass);
	}


}