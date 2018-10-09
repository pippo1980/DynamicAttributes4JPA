package com.sirius.jpa.dynamic.attribute;

import org.springframework.data.annotation.Transient;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Transient
@Documented
public @interface DynamicAttributesGroup {

    String name() default "default";

}
