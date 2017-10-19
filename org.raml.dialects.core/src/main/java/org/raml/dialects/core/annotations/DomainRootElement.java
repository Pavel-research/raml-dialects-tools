package org.raml.dialects.core.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.raml.dialects.core.DefaultParser;
import org.raml.dialects.core.IParser;

@Retention(RetentionPolicy.RUNTIME)
public @interface DomainRootElement {
	Class<? extends IParser<?>> parser() default DefaultParser.class;
	Class<?>[] dependencies() default {};
	String version() default "1.0";
	String name() default "";
	
}
