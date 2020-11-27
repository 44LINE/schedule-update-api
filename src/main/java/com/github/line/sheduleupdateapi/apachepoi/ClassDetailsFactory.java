package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.*;
import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClassDetailsFactory implements EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> {
    private final FixedRowMapper fixedRowMapper;

    public ClassDetailsFactory(@Autowired FixedRowMapper fixedRowMapper) {
        this.fixedRowMapper = fixedRowMapper;
    }

    @Override
    public Optional<ClassDetails> create(GroupedDailySchedule groupedDailySchedule, Pair<Integer, String> argument) {
        System.out.println("arg" + argument);

        if(!argument.getValue().isEmpty()) {

            DayTimePeriods dayTimePeriod = DayTimePeriods.retrieveDayTimePeriod(argument.getKey());
            ClassPeriod classPeriod = new ClassPeriod(dayTimePeriod);
            Optional<Pair<Lecturer, ClassObject>> pair = fixedRowMapper.mapToLecturerAndClassObjectPair(argument.getValue());
            System.out.println("dtp" + dayTimePeriod.toString() + " lecturer: " + pair.get().getKey().getSurname() + " co: " + pair.get().getValue().getName());

            if (pair.isPresent()) {
                return Optional.of(new ClassDetails(
                        null,
                        pair.get().getValue(),
                        pair.get().getKey(),
                        ClassType.retrieveClassType(argument.getValue()),
                        classPeriod,
                        groupedDailySchedule
                ));
            }
        }
        return Optional.empty();
    }
}
