package org.raml.jsonld2toplevel.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.raml.jsonld2toplevel.DefaultParser;
import org.raml.jsonld2toplevel.IParser;

@Retention(RetentionPolicy.RUNTIME)
public @interface DomainRootElement {
	Class<? extends IParser<?>> parser() default DefaultParser.class;
	Class<?>[] dependencies() default {};
	String version() default "1.0";
	String name() default "";
	
}
