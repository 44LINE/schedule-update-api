package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ClassObject;
import com.github.line.sheduleupdateapi.repository.ClassObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ClassObjectService {
    private final ClassObjectRepository repository;

    public ClassObjectService(@Autowired ClassObjectRepository repository) {
        this.repository = repository;
    }

    public List<ClassObject> getAll() {
        return Collections.unmodifiableList(repository.findAll());
    }
}
