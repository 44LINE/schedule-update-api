package com.github.line.sheduleupdateapi.domain;

import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassDetailsTest {
    @Test
    public void toStringTest() {
        //given
        ClassObject classObject = new ClassObject(1L, "sampleName", "sN");
        Lecturer lecturer = new Lecturer(1L, "sampleLecturer", "surname", "shortName", null);
        ClassDetails classDetails = new ClassDetails(1L, classObject, lecturer, ClassType.LECTURE, new ClassPeriod(DayTimePeriods.MORNING), new GroupedDailySchedule());

        System.out.println(classDetails.toString());
    }

}