package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.ClassObject;
import com.github.line.sheduleupdateapi.domain.ClassPeriod;
import com.github.line.sheduleupdateapi.domain.Lecturer;
import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

class ClassDetailsRepositoryTest {

    @Autowired
    private ClassDetailsRepository classDetailsRepository;

    @Autowired
    private ClassObjectRepository classObjectRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Test
    public void addAndGet_returnsTrue() {

        //given
        ClassObject classObject = classObjectRepository.getOne(1L);
        Lecturer lecturer = lecturerRepository.getOne(1L);
        ClassPeriod period = new ClassPeriod(DayTimePeriods.MORNING);

        ClassDetails classDetails = new ClassDetails(
                null,
                classObject,
                lecturer,
                ClassType.LECTURE,
                period,
                null
        );

        //when
        classDetailsRepository.save(classDetails);

        //then
        ClassDetails classDetailsReturned = classDetailsRepository.getOne(1L);

        Assertions.assertEquals(classDetails, classDetailsReturned);
    }
}