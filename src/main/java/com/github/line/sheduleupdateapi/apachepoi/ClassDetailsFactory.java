package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.*;
import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.enums.DayTimePeriods;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ClassDetailsFactory implements EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final FixedRowMapper fixedRowMapper;

    public ClassDetailsFactory(@Autowired FixedRowMapper fixedRowMapper) {
        this.fixedRowMapper = fixedRowMapper;
    }

    @Override
    public Optional<ClassDetails> create(GroupedDailySchedule groupedDailySchedule, Pair<Integer, String> argument) {
        if(!argument.getValue().isEmpty()) {
            DayTimePeriods dayTimePeriod = DayTimePeriods.retrieveDayTimePeriodSwitch(argument.getKey());
            ClassPeriod classPeriod = new ClassPeriod(dayTimePeriod);
            //logger.info("Index: " + argument.getKey() + " Value: " + argument.getValue());
            Optional<Pair<Lecturer, ClassObject>> pair = fixedRowMapper.mapToLecturerAndClassObjectPair(argument.getValue());
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
