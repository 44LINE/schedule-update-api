package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.ClassObject;
import com.github.line.sheduleupdateapi.domain.Lecturer;
import com.github.line.sheduleupdateapi.service.ClassObjectService;
import com.github.line.sheduleupdateapi.service.LecturerService;
import javafx.util.Pair;

import java.util.*;

public final class FixedRowMapper {
    private final List<Lecturer> lecturers;
    private final List<ClassObject> classObjects;

    private Map<Pair<String, String>, Long> lecturersKeywords;
    private Map<Pair<String, String>, Long> classObjectsKeywords;

    private FixedRowMapper() {
        throw new AssertionError();
    }

    public FixedRowMapper(LecturerService lecturerService, ClassObjectService classObjectService) {
        this.lecturers = lecturerService.getAll();
        this.classObjects = classObjectService.getAll();
        init();
    }


    //less-code solution
    public Optional<Pair<Lecturer, ClassObject>> mapToLecturerAndClassObjectPair(String rowValue) {
        for (Lecturer lecturer: lecturers
             ) {
            if (rowValue.contains(lecturer.getSurname()) || rowValue.contains(lecturer.getShortName())) {
                Optional<Lecturer> matchingLecturer = Optional.of(lecturer);

                for (ClassObject classObject: classObjects
                ) {
                    if (rowValue.contains(classObject.getName()) || rowValue.contains(classObject.getShortName())) {
                        Optional<ClassObject> matchingClassObject = Optional.of(classObject);
                        return Optional.of(new Pair(matchingLecturer.get(), matchingClassObject.get()));
                    }
                }
            }
        }
        return Optional.empty();
    }

    //using additional collections
    public Optional<Pair<Lecturer, ClassObject>> findEntitiesByRowValue(String rowValue) {
        Optional<Long> classObjectId = contains(classObjectsKeywords, rowValue);
        if (classObjectId.isPresent()) {
            Optional<Long> lecturerId = contains(lecturersKeywords, rowValue);
            if (lecturerId.isPresent()) {
                Pair<Lecturer, ClassObject> pair = new Pair(lecturers.get(classObjectId.get().intValue()), classObjects.get(classObjectId.get().intValue()));
                return Optional.of(pair);
            }
        }
        return Optional.empty();
    }

    private Optional<Long> contains(Map<Pair<String, String>, Long> strings, String rowValue) {
        for (Pair<String, String> pair: strings.keySet()
             ) {
            if (rowValue.contains(pair.getKey()) || rowValue.contains(pair.getValue())) {
                return Optional.of(strings.get(pair));
            }
        }
        return Optional.empty();
    }

    private void init() {
        for (Lecturer lecturer: this.lecturers
             ) {
            Pair<String, String> surnameAndShortName = new Pair(lecturer.getSurname(), lecturer.getShortName());
            this.lecturersKeywords.put(surnameAndShortName, lecturer.getId());
        }

        for (ClassObject classObject: this.classObjects
            ) {
            Pair<String, String> nameAndShortName = new Pair(classObject.getName(), classObject.getShortName());
            this.classObjectsKeywords.put(nameAndShortName, classObject.getId());
        }

        this.lecturersKeywords = Collections.unmodifiableMap(this.lecturersKeywords);
        this.classObjectsKeywords = Collections.unmodifiableMap(this.classObjectsKeywords);
    }
}
