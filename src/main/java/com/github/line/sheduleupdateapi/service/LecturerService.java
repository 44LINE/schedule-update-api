package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Lecturer;
import com.github.line.sheduleupdateapi.repository.LecturerRepository;

import java.util.Collections;
import java.util.List;

public class LecturerService {
    private final LecturerRepository repository;

    private LecturerService() {
        throw new AssertionError();
    }

    public LecturerService(LecturerRepository repository) {
        this.repository = repository;
    }

    public List<Lecturer> getAll() {
        return Collections.unmodifiableList(repository.findAll());
    }
}
