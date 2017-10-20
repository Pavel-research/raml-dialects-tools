package org.aml.example.simple.dialects2java;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.junit.Test;
import org.raml.dialects.profileNode;
import org.raml.dialects.core.DialectRegistry;
import org.skyscreamer.jsonassert.JSONAssert;

import junit.framework.TestCase;

public class BasicTest extends TestCase {

	@Test
	/**
	 * load from json ld with known instance type
	 */
	public void test0() {
		profileNode profile = DialectRegistry.getDefault().parse(BasicTest.class.getResource("/test.json"),
				profileNode.class);
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");
	}

	/**
	 * load from json ld with uknown instance type
	 */
	@Test
	public void test() {
		DialectRegistry.getDefault().registerClass(profileNode.class);
		profileNode profile = (profileNode) DialectRegistry.getDefault()
				.parse(BasicTest.class.getResource("/test.json"));
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");
	}

	@Test
	/**
	 * store to jsonld
	 */
	public void test1() {
		DialectRegistry.getDefault().registerClass(profileNode.class);
		profileNode profile = (profileNode) DialectRegistry.getDefault()
				.parse(BasicTest.class.getResource("/test.json"));
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");
		try {
			JSONArray jsonld = DialectRegistry.getDefault().toJSONLD(profile,
					"file:/shared/src/test/resources/vocabularies/validation_profile_example.raml");
			// System.out.println(jsonld.toString(2));
			JSONAssert.assertEquals(jsonld,
					new JSONArray(new JSONTokener(BasicTest.class.getResourceAsStream("/test.json"))), true);
		} catch (JSONException e) {
			fail();
		}
	}

	@Test
	/**
	 * load from jsonld and store to simple json
	 */
	public void test2() {
		DialectRegistry.getDefault().registerClass(profileNode.class);
		profileNode profile = (profileNode) DialectRegistry.getDefault()
				.parse(BasicTest.class.getResource("/test.json"));
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");
		try {
			JSONObject jsonld = DialectRegistry.getDefault().toJSON(profile);
			// System.out.println(jsonld.toString(2));
			JSONAssert.assertEquals(jsonld,
					new JSONObject(new JSONTokener(BasicTest.class.getResourceAsStream("/test-simple.json"))), true);
		} catch (JSONException e) {
			fail();
		}
	}

	@Test
	/**
	 * load from simple json and store to simple json
	 */
	public void test3() {
		DialectRegistry.getDefault().registerClass(profileNode.class);
		profileNode profile = (profileNode) DialectRegistry.getDefault()
				.parse(BasicTest.class.getResource("/test-simple.json"), profileNode.class);
		assertEquals(profile.getVersion(), "0.1-SNAPSHOT");
		try {
			JSONObject jsonld = DialectRegistry.getDefault().toJSON(profile);
			// System.out.println(jsonld.toString(2));
			JSONAssert.assertEquals(jsonld,
					new JSONObject(new JSONTokener(BasicTest.class.getResourceAsStream("/test-simple.json"))), true);
		} catch (JSONException e) {
			fail();
		}
	}
}