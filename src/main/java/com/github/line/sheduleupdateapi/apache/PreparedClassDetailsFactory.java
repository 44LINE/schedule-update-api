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
    public Iterable<ClassDetails> create(Collection<? extends Object> queue, Entity selfReference) {
        if (!(queue instanceof LinkedBlockingQueue || queue.getClass().getComponentType().equals(String.class) || selfReference instanceof GroupedDailySchedule)) {
            throw new IllegalArgumentException();
        }

        HashSet<ClassDetails> classDetails = queue
                .stream()
                .map(item -> create(item, selfReference).get())
                .collect(Collectors.toCollection(HashSet::new));

        rowIndex.lazySet(0);
        return Collections.unmodifiableSet(classDetails);
    }

    @Override
    public Optional<ClassDetails> create(Object argument, Entity selfReference) {
        DayTimePeriods dayTimePeriod = DayTimePeriods.retrieveDayTimePeriod(rowIndex.getAndIncrement());
        ClassPeriod classPeriod = new ClassPeriod(dayTimePeriod);
        Optional<Pair<Lecturer, ClassObject>> pair = fixedRowMapper.findEntitiesByRowValue((String) argument);

        if (pair.isPresent()) {
            return Optional.of(new ClassDetails(
                    null,
                    pair.get().getValue(),
                    pair.get().getKey(),
                    ClassType.retrieveClassType((String) argument),
                    classPeriod,
                    (GroupedDailySchedule) selfReference
            ));
        }
        return Optional.empty();
    }


}
