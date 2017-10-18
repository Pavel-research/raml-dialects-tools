package org.raml.vocabularies;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.raml.vocabularies.dto.ClassTermDTO;
import org.raml.vocabularies.dto.PropertyTermDTO;
import org.raml.vocabularies.dto.VocabularyDTO;
import org.yaml.snakeyaml.Yaml;

public class Vocabulary extends Base {

	private static Yaml yaml = new Yaml();

	private String base;

	private String dialect;

	private String version; 

	private String usage;

	private Map<String, Vocabulary> uses = new LinkedHashMap<String, Vocabulary>();

	private Map<String, String> external = new LinkedHashMap<String, String>();

	private Map<String, PropertyTerm> propertyTerms = new LinkedHashMap<String, PropertyTerm>();

	private Map<String, ClassTerm> classTerms = new LinkedHashMap<String, ClassTerm>();

	private URL location;

	public URL getLocation() {
		return location;
	}

	public void setLocation(URL location) {
		this.location = location;
	}

	public Vocabulary() {
		
	}
	
	public Vocabulary(URL load) throws IOException {
		super();
		this.location = load;
		VocabularyDTO pojoList = yaml.loadAs(load.openStream(), VocabularyDTO.class);
		Map<String, String> uses = pojoList.getUses();
		for (String nm : uses.keySet()) {
			try {
				this.uses.put(nm, new Vocabulary(load.toURI().resolve(uses.get(nm)).toURL()));
			} catch (URISyntaxException e) {
				throw new IOException(e);
			}
		}
		Base.copyFrom(pojoList, this);
		Map<String, ClassTermDTO> classes = pojoList.getClassTerms();
		for (String nm : classes.keySet()) {
			this.classTerms.put(nm, new ClassTerm(nm,this,classes.get(nm)));
		}
		Map<String, PropertyTermDTO> props = pojoList.getPropertyTerms();
		for (String nm : props.keySet()) {
			this.propertyTerms.put(nm, new PropertyTerm(nm,this,props.get(nm)));
		}
		this.classTerms.values().forEach(v->v.resolve());
		this.propertyTerms.values().forEach(v->v.resolve());
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

	public Map<String, Vocabulary> getUses() {
		return uses;
	}

	public void setUses(Map<String, Vocabulary> uses) {
		this.uses = uses;
	}

	public Map<String, String> getExternal() {
		return external;
	}

	public void setExternal(Map<String, String> external) {
		this.external = external;
	}

	public Map<String, PropertyTerm> getPropertyTerms() {
		return propertyTerms;
	}

	public void setPropertyTerms(Map<String, PropertyTerm> propertyTerms) {
		this.propertyTerms = propertyTerms;
	}

	public Map<String, ClassTerm> getClassTerms() {
		return classTerms;
	}

	public void setClassTerms(Map<String, ClassTerm> classTerms) {
		this.classTerms = classTerms;
	}

	public PropertyTerm resolveProperty(String range) {
		PropertyTerm ct=this.propertyTerms.get(range);
		if (ct!=null) {
			return ct;
		}
		int dt=range.indexOf('.');
		if (dt!=-1) {
			String nms=range.substring(0,dt);
			String localName=range.substring(dt+1);
			Vocabulary vocabulary = this.uses.get(nms);
			if (vocabulary!=null) {
				return vocabulary.resolveProperty(localName);
			}
		}
		return null;		
	}
	public DataType resolveClass(String range) {
		if (range==null) {
			range="string";
		}
		ClassTerm ct=this.classTerms.get(range);
		if (ct!=null) {
			return ct;
		}
		int dt=range.indexOf('.');
		if (dt!=-1) {
			String nms=range.substring(0,dt);
			String localName=range.substring(dt+1);
			Vocabulary vocabulary = this.uses.get(nms);
			if (vocabulary!=null) {
				return vocabulary.resolveClass(localName);
			}
		}
		for (Builtins b:Builtins.values()) {
			if (b.getName().equals(range)) {
				return b;
			}
		}
		return null;		
	}
}
