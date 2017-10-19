package org.raml.jsonld2toplevel.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BuiltinInstances {
	Class<?>[] value();
}
