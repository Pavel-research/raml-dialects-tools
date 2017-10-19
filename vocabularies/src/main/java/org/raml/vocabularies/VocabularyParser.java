package org.raml.vocabularies;

import java.io.Reader;
import java.net.URI;

import org.raml.dialects.core.IParser;

public class VocabularyParser implements IParser<Vocabulary>{

	@Override
	public Class<Vocabulary> result() {
		return Vocabulary.class;
	}

	@Override
	public Vocabulary parse(Reader reader, URI location, Class<? extends Vocabulary> target) {
		Vocabulary v = new Vocabulary();
		try {
			v.load(location.toURL(), reader);
		} catch (Exception e) {
			throw new IllegalStateException(e);			
		}
		return v;
	}

	@Override
	public String[] supportedExtensions() {
		return new String[]{"yaml","yml","raml"};
	}

}
