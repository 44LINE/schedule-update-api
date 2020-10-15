package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.*;
import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import javafx.util.Pair;

import javax.persistence.Entity;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PreparedClassDetailsFactory implements PreparedEntityFactory {
    private AtomicInteger rowIndex = new AtomicInteger(0);
    private final FixedRowMapper fixedRowMapper;

    private PreparedClassDetailsFactory() {
        throw new AssertionError();
    }

    public PreparedClassDetailsFactory(FixedRowMapper fixedRowMapper) {
        this.fixedRowMapper = fixedRowMapper;
    }

    @Override
    public Iterable<ClassDetails> create(Collection<? extends Object> dailyClasses, Entity groupedDailyScheduleReference) {
        if (!(dailyClasses instanceof LinkedBlockingQueue || dailyClasses.getClass().getComponentType().equals(String.class) || groupedDailyScheduleReference instanceof GroupedDailySchedule)) {
            throw new IllegalArgumentException();
        }

        List<ClassDetails> classDetails = dailyClasses
                .stream()
                .map(item -> create(item, groupedDailyScheduleReference)
                        .orElseThrow(IllegalStateException::new))
                .collect(Collectors.toCollection(ArrayList::new));

        rowIndex.lazySet(0);
        return Collections.unmodifiableList(classDetails);
    }

    @Override
    public Optional<ClassDetails> create(Object argument, Entity groupedDailyScheduleReference) {
        DayTimePeriods dayTimePeriod = DayTimePeriods.retrieveDayTimePeriod(rowIndex.getAndIncrement());
        ClassPeriod classPeriod = new ClassPeriod(dayTimePeriod);
        Optional<Pair<Lecturer, ClassObject>> pair = fixedRowMapper.findEntitiesByRowValue((String) argument);

        if (pair.isPresent()) {
            rowIndex.getAndIncrement(); rowIndex.getAndIncrement();
            return Optional.of(new ClassDetails(
                    null,
                    pair.get().getValue(),
                    pair.get().getKey(),
                    ClassType.retrieveClassType((String) argument),
                    classPeriod,
                    (GroupedDailySchedule) groupedDailyScheduleReference
            ));
        }

        return Optional.empty();
    }


}
