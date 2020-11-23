package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.*;
import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;

import javax.persistence.Entity;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

;

public class PreparedClassDetailsFactory implements PreparedEntityFactory {
    private final AtomicInteger rowIndex = new AtomicInteger(0);
    private final FixedRowMapper fixedRowMapper;

    private PreparedClassDetailsFactory() {
        throw new AssertionError();
    }

    public PreparedClassDetailsFactory(FixedRowMapper fixedRowMapper) {
        this.fixedRowMapper = fixedRowMapper;
    }

    @Override
    public Iterable<ClassDetails> create(Collection<? extends Object> collection, Entity groupedDailyScheduleReference) {
        if (!(collection instanceof List || groupedDailyScheduleReference instanceof GroupedDailySchedule)) {
            throw new IllegalArgumentException();
        }

        List<String> singleDayClasses = (List<String>) collection;
        /*
        List<ClassDetails> classDetails = singleDayClasses
                .stream()
                .map(item -> create(item, groupedDailyScheduleReference).get())
                .collect(Collectors.toCollection(ArrayList::new));
         */
        List<ClassDetails> classDetails = new ArrayList<>();
        for (String s: singleDayClasses) {
            Optional<ClassDetails> cd = create(s, groupedDailyScheduleReference);
            cd.ifPresent(classDetails::add);
        }

        rowIndex.lazySet(0);
        return Collections.unmodifiableList(classDetails);
    }

    @Override
    public Optional<ClassDetails> create(Object argument, Entity groupedDailyScheduleReference) {
        System.out.println("arg" + argument);

        if(argument != null && !((String) argument).isEmpty()) {

            DayTimePeriods dayTimePeriod = DayTimePeriods.retrieveDayTimePeriod(rowIndex.getAndIncrement());
            ClassPeriod classPeriod = new ClassPeriod(dayTimePeriod);
            Optional<Pair<Lecturer, ClassObject>> pair = fixedRowMapper.mapToLecturerAndClassObjectPair((String) argument);
            System.out.println("dtp" + dayTimePeriod.toString() + " lecturer: " + pair.get().getKey().getSurname() + " co: " + pair.get().getValue().getName());

            if (pair.isPresent()) {

                rowIndex.getAndIncrement();
                rowIndex.getAndIncrement();
                return Optional.of(new ClassDetails(
                        null,
                        pair.get().getValue(),
                        pair.get().getKey(),
                        ClassType.retrieveClassType((String) argument),
                        classPeriod,
                        (GroupedDailySchedule) groupedDailyScheduleReference
                ));
            }
        }
        return Optional.empty();
    }


}
