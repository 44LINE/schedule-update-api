package com.github.line.sheduleupdateapi.service;

import javax.persistence.Entity;
import java.io.Serializable;
import java.lang.annotation.Annotation;

//Interface let Entity annotated class to be used as have Entity type
public interface EntityType extends Entity, Serializable {
    @Override
    default String name() {
        throw new UnsupportedOperationException();
    }

    @Override
    default Class<? extends Annotation> annotationType() {
        throw new UnsupportedOperationException();
    }
}
