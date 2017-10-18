package org.raml.vocabularies;

import org.raml.vocabularies.dto.BaseTermDTO;

public class BaseTerm<T extends BaseTermDTO> {

	
	private String name;
	private Vocabulary vocabulary;
	protected T dto;
	
	public void setName(String name) {
		dto.setName(name);
	}

	public String getDisplayName() {
		return dto.getDisplayName();
	}

	public void setDisplayName(String displayName) {
		dto.setDisplayName(displayName);
	}

	public String getDescription() {
		return dto.getDescription();
	}

	public void setDescription(String description) {
		dto.setDescription(description);
	}

	public BaseTerm(String name,Vocabulary vocabulary,T dto) {
		super();
		this.name=name;
		this.vocabulary=vocabulary;
		this.dto=dto;
	}
	
	public String getName() {
		return name;
	}

	public Vocabulary getVocabulary() {
		return vocabulary;
	}

}
