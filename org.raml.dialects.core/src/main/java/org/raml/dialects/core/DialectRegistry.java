package org.raml.dialects.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.raml.dialects.core.annotations.ClassTerm;
import org.raml.dialects.core.annotations.DomainRootElement;

public class DialectRegistry {

	static class ParserRecord<T> {
		@SuppressWarnings("unchecked")
		public ParserRecord(IParser<?> parser, Class<?> target) {
			this.parser = (IParser<T>) parser;
			this.clazz = (Class<T>) target;
		}

		IParser<T> parser;
		Class<T> clazz;
	}

	public static class ParserKey {
		protected final String extension;
		protected final String header;

		public ParserKey(String extension, String header) {
			super();
			this.extension = extension;
			this.header = header;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((extension == null) ? 0 : extension.hashCode());
			result = prime * result + ((header == null) ? 0 : header.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ParserKey other = (ParserKey) obj;
			if (extension == null) {
				if (other.extension != null)
					return false;
			} else if (!extension.equals(other.extension))
				return false;
			if (header == null) {
				if (other.header != null)
					return false;
			} else if (!header.equals(other.header))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "ParserKey [header=" + header + "]";
		}
	}

	protected LinkedHashMap<ParserKey, ParserRecord<?>> map = new LinkedHashMap<ParserKey, ParserRecord<?>>();
	protected HashSet<Class<?>> knownClasses = new HashSet<Class<?>>();

	public void registerParser(String header, String extension, IParser<?> parser, Class<?> target) {
		map.put(new ParserKey(extension, header), new ParserRecord<Object>(parser, target));
	}

	DefaultParser defaultParser = new DefaultParser();

	public Object parse(URI location) {
		return parse(location, Object.class);
	}

	public Object parse(URL location) {
		return parse(location, Object.class);
	}

	public <T> T parse(URL location, Class<T> clazz) {
		try {
			return this.parse(location.toURI(), clazz);
		} catch (URISyntaxException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings({ })
	public <T> T parse(URI location, Class<T> clazz) {
		try {
			String readStream = readStream(location.toURL().openStream());
			return parse(location, clazz, readStream);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T parse(URI location, Class<T> clazz, String content)
			throws Exception {
		registerClass(clazz);
		String sm = location.toString();
		int ls = sm.lastIndexOf('.');
		String extension = "json";
		if (ls != -1) {
			extension = sm.substring(ls + 1);
		}
		

		String firstLine = getFirstLine(content);
		ParserRecord<?> parserRecord = map.get(new ParserKey(extension, firstLine));
		IDataAdapter adapter = null;
		if (parserRecord == null) {
			try {
				adapter = (IDataAdapter) Class.forName("org.raml.dialects." + extension.toUpperCase())
						.newInstance();
				parserRecord = map.get(new ParserKey("json", firstLine));
			} catch (ClassNotFoundException e) {
				// does not know format
			}
		}
		if (parserRecord == null) {
			if (extension.equals("json")
					&& (clazz.getAnnotation(ClassTerm.class) != null || content.trim().startsWith("["))) {

				return clazz.cast(defaultParser.parse(new StringReader(content), location, clazz));
			}
			throw new IllegalStateException("Does not know how to obtain parser for header:" + firstLine);
		}
		IParser<?> iParser = parserRecord.parser;
		if (adapter != null) {
			JSONOutput adaptToJson = adapter.adaptToJson(new StringReader(content), location,
					parserRecord.clazz);
			if (iParser instanceof IJSONParser) {
				return (T) clazz
						.cast(((IJSONParser<?>) iParser).parse(adaptToJson, location, (Class) parserRecord.clazz));
			} else {
				content = adaptToJson.toString();
			}
		}
		return (T) clazz.cast(iParser.parse(new StringReader(content), location, (Class) parserRecord.clazz));
	}

	public <T> void registerClass(Class<T> clazz) {
		try {
			if (!knownClasses.contains(clazz)) {
				knownClasses.add(clazz);
				DomainRootElement annotation = clazz.getAnnotation(DomainRootElement.class);
				if (annotation != null) {
					Class<? extends IParser<?>> parser = annotation.parser();
					String header =header(annotation,clazz);
					IParser<?> newInstance = parser.newInstance();
					for (String s : newInstance.supportedExtensions()) {
						map.put(new ParserKey(s, header), new ParserRecord<T>(newInstance, clazz));
					}
					for (Class<?> c : annotation.dependencies()) {
						registerClass(c);
					}
				}
				ClassTerm annotation2 = clazz.getAnnotation(ClassTerm.class);
				if (annotation2 != null) {
					defaultParser.register(clazz);
				}
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	static String readStream(InputStream inputStream) {
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in;
		try {
			in = new InputStreamReader(inputStream, "UTF-8");
			for (;;) {
				int rsz = in.read(buffer, 0, buffer.length);
				if (rsz < 0)
					break;
				out.append(buffer, 0, rsz);
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return out.toString();
	}

	static String getFirstLine(String rs) {
		try {
			return new BufferedReader(new StringReader(rs)).readLine();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	static final DialectRegistry registry = new DialectRegistry();

	public static DialectRegistry getDefault() {
		return registry;
	}

	public JSONArray toJSONLD(Object toSerialize, String iri) {
		return DefaultParser.AMFJSONLD.writeJSONLD(toSerialize, iri);
	}

	public JSONObject toJSON(Object toSerialize) {
		return DefaultParser.AMFJSONLD.writeJSON(toSerialize);
	}

	public String storeAs(Object toSerialize, Class<? extends IDataAdapter> adapter) {
		try {
			return adapter.newInstance().toStringRepresentation(toJSON(toSerialize), toSerialize.getClass());
		} catch (InstantiationException e) {
			throw new IllegalStateException(e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

	public static String header(DomainRootElement annotation,Class<?>cl) {
		String name = annotation.name();
		if (name.indexOf(' ')==-1){
			if (name.length()==0){
				name=cl.getSimpleName();
			}
			return "#%RAML " + annotation.version() + " " + name;	
		}
		return "#%" + name + " " + annotation.version();
	}
}