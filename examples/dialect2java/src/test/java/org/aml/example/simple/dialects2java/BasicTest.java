package org.aml.example.simple.dialects2java;

import org.junit.Test;
import org.raml.dialects.profileNode;
import org.raml.dialects.core.DialectRegistry;

import junit.framework.TestCase;

public class BasicTest extends TestCase{

	
	@Test
	public void test0() { 
		profileNode profile = DialectRegistry.getDefault().parse(BasicTest.class.getResource("/test.json"),profileNode.class);
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");		
	}
	@Test
	public void test() { 
		DialectRegistry.getDefault().registerClass(profileNode.class);
		profileNode profile = (profileNode) DialectRegistry.getDefault().parse(BasicTest.class.getResource("/test.json"));
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");		
	}
}
