package org.raml.vocabularies;

import java.util.ArrayList;
import java.util.List;

import org.raml.vocabularies.dto.PropertyTermDTO;

public class LocalPropertyTerm extends BaseTerm<PropertyTermDTO> implements PropertyTerm{

	public LocalPropertyTerm(String name, Vocabulary vocabulary, PropertyTermDTO dto) {
		super(name, vocabulary, dto);
	}
	protected DataType _range;
	protected PropertyTerm _base;
	protected ArrayList<PropertyTerm>subProperties=new ArrayList<>();
	
	public ArrayList<String>getSameAs(){
		return this.dto.getSameAs();
	}
	@Override
	public String toString() {
		return "P:"+this.getName();
	}
	public DataType getRange(){
		return this._range;	
	}
	public PropertyTerm getSuperProperty() {
		return this._base;
	}
	public List<PropertyTerm> getSubProperties() {
		return this.subProperties;
	}
	public void resolve() {
		DataType resolveClass = this.getVocabulary().resolveClass(this.dto.getRange());
		if (resolveClass==null) {
			throw new IllegalStateException("can not resolve range:"+this.dto.getRange());
		}
		this._range=resolveClass;
		Object extends1 = this.dto.getExtends();
		if (extends1!=null) {
			Object oj=this.dto.getExtends();
			PropertyTerm base=this.getVocabulary().resolveProperty((String) oj);
			if (base==null) {
				throw new IllegalStateException("can not resolve super property:"+this.dto.getExtends());
			}
			if (base instanceof LocalPropertyTerm){
				((LocalPropertyTerm)base).subProperties.add(this);
			}
			this._base=base;
		}
	}
}
