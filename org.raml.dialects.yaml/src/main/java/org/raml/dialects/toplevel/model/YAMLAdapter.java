package org.raml.dialects.toplevel.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.raml.jsonld2toplevel.AMFJSONLD;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.ScalarNode;
import org.yaml.snakeyaml.representer.Representer;

public class YAMLAdapter {

	private static final AMFJSONLD AMFJSONLD = new AMFJSONLD();

	private static Yaml yaml;
	static {
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		yaml = new Yaml(dumperOptions);
	}

	public static <T> T load(InputStream stream, Class<T> topNode, URI curent) {
		Yaml yaml = createYAMLInstance(curent);
		Object obj = yaml.load(stream);
		return AMFJSONLD.readFromJSON((JSONObject) new YAMLAdapter()._convertToJson(obj), topNode);
	}

	public static <T> T load(URL curent, Class<T> topNode) {
		try {
			Yaml yaml = createYAMLInstance(curent.toURI());
			Object obj = yaml.load(curent.openStream());
			return AMFJSONLD.readFromJSON((JSONObject) new YAMLAdapter()._convertToJson(obj), topNode);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static class IncludeNode {
		URI uri;

		public IncludeNode(URI uri) {
			super();
			this.uri = uri;
		}
	}

	private static Yaml createYAMLInstance(URI resolver) {
		DumperOptions dumperOptions = new DumperOptions();
		dumperOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		Yaml yaml = new Yaml(new Constructor() {

			protected Object constructObject(Node node) {
				if (node.getTag().getValue().equals("!include")) {
					ScalarNode sn = (ScalarNode) node;
					String path = sn.getValue();
					URI resolve = resolver.resolve(path);
					return new IncludeNode(resolve);
				}
				return super.constructObject(node);
			}

		}, new Representer(), dumperOptions);
		return yaml;
	}

	public static <T> T load(Reader stream, Class<T> topNode, URI resolver) {
		Yaml yaml = createYAMLInstance(resolver);
		Object obj = yaml.load(stream);

		return AMFJSONLD.readFromJSON((JSONObject) new YAMLAdapter()._convertToJson(obj), topNode);
	}

	public static <T> T load(String content, Class<T> topNode, URI resolver) {
		Yaml yaml = createYAMLInstance(resolver);
		Object obj = yaml.parse(new StringReader(content));
		return AMFJSONLD.readFromJSON((JSONObject) new YAMLAdapter()._convertToJson(obj), topNode);
	}

	public static String toYAML(Object node) {
		JSONObject writeJSON = new AMFJSONLD().writeJSON(node);
		String prettyJSONString = writeJSON.toString(4);
		@SuppressWarnings("unchecked")
		Map<String, Object> map = (Map<String, Object>) yaml.load(prettyJSONString);
		return yaml.dump(map);
	}

	protected HashMap<String, Object> resolved = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	private Object _convertToJson(Object o) throws JSONException {
		if (o instanceof IncludeNode) {
			IncludeNode nm = (IncludeNode) o;
			Yaml yaml = createYAMLInstance(nm.uri);
			try {
				//FIXME more correct include resolution
				if (resolved.containsKey(nm.uri.toString())){
					return resolved.get(nm.uri.toString());
				}
				Object obj = yaml.load(nm.uri.toURL().openStream());
				resolved.put(nm.uri.toString(), obj);
				return _convertToJson(obj);
			} catch (MalformedURLException e) {
				throw new IllegalStateException(e);
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		if (o instanceof Map) {
			Map<Object, Object> map = (Map<Object, Object>) o;
			JSONObject result = new JSONObject();
			for (Map.Entry<Object, Object> stringObjectEntry : map.entrySet()) {
				String key = stringObjectEntry.getKey().toString();
				result.put(key, _convertToJson(stringObjectEntry.getValue()));
			}

			return result;
		} else if (o instanceof ArrayList) {
			@SuppressWarnings("rawtypes")
			ArrayList arrayList = (ArrayList) o;
			JSONArray result = new JSONArray();

			for (Object arrayObject : arrayList) {
				result.put(_convertToJson(arrayObject));
			}
			return result;
		} else if (o instanceof String) {
			return o;
		} else if (o instanceof Boolean) {
			return o;
		} else {
			return o;
		}
	}

}