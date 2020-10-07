package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ClassObject;
import com.github.line.sheduleupdateapi.repository.ClassObjectRepository;

import java.util.Collections;
import java.util.List;

public class ClassObjectService {
    private final ClassObjectRepository repository;

    private ClassObjectService() {
        throw new AssertionError();
    }

    public ClassObjectService(ClassObjectRepository repository) {
        this.repository = repository;
    }

    public List<ClassObject> getAll() {
        return Collections.unmodifiableList(repository.findAll());
    }
}
