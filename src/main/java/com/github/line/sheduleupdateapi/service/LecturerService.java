package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Lecturer;
import com.github.line.sheduleupdateapi.repository.LecturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class LecturerService {
    private final LecturerRepository repository;

    public LecturerService(@Autowired LecturerRepository repository) {
        this.repository = repository;
    }

    public List<Lecturer> getAll() {
        return Collections.unmodifiableList(repository.findAll());
    }
}
