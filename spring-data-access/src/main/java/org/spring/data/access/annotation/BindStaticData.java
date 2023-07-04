package org.spring.data.access.annotation;

import org.spring.data.access.model.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindStaticData {
    String fileLocation();

    Class<? extends BaseEntity> castTo();
}