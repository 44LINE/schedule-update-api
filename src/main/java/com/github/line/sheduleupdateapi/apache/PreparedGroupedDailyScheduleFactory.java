package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.*;

import static com.github.line.sheduleupdateapi.apache.CustomFixedIndexes.*;

public class PreparedGroupedDailyScheduleFactory implements PreparedEntityFactory {
    private final PreparedClassDetailsFactory preparedClassDetailsFactory;

    private PreparedGroupedDailyScheduleFactory() {
        throw new AssertionError();
    }

    public PreparedGroupedDailyScheduleFactory(PreparedClassDetailsFactory preparedClassDetailsFactory) {
        this.preparedClassDetailsFactory = preparedClassDetailsFactory;
    }

    @Override
    public Iterable<? extends Entity> create(Collection<?> collection, Entity scheduleReference) {
        if (!(collection instanceof List || scheduleReference instanceof Schedule)) {
            throw new IllegalArgumentException();
        }

        List<List<String>> columnsContent = (List<List<String>>) collection;
        Map<Pair<Integer, LocalDate>, List<String>> splittedColumnsContentByDate = new HashMap<>();
        List<GroupedDailySchedule> groupedDailySchedules = new ArrayList<>();
        //todo

        for (List<String> columnContent: columnsContent) {
            List<String> dailyClasses = new ArrayList<>();
            int dateIndex = 0;

            for (String rowContent: columnContent) {
                if (dailyClasses.size() == STATIC_DAILY_ROW_NUMBER) {
                    Pair<Integer, LocalDate> groupAndDate = new Pair<>(columnsContent.indexOf(columnContent), getDailyScheduleDate(dateIndex++));
                    splittedColumnsContentByDate.put(groupAndDate, dailyClasses);
                    dailyClasses = new ArrayList<>();

                    if (!(rowContent.matches(DAILY_SCHEDULE_SPLITTER_0) || rowContent.matches(DAILY_SCHEDULE_SPLITTER_1))) {
                        break;
                    }
                } else if (rowContent.matches(DAILY_SCHEDULE_SPLITTER_0) || rowContent.matches(DAILY_SCHEDULE_SPLITTER_1)) {
                    dailyClasses = new ArrayList<>();
                } else {
                    dailyClasses.add(rowContent);
                }
            }
        }

        for (Pair<Integer, LocalDate> groupAndDate: splittedColumnsContentByDate.keySet()) {
            Pair<Pair<Integer, LocalDate>, List<String>> mapElement = new Pair<>(groupAndDate, splittedColumnsContentByDate.get(groupAndDate));
            groupedDailySchedules.add((GroupedDailySchedule) create(mapElement, scheduleReference)
                    .orElseThrow(IllegalStateException::new));
        }

        return groupedDailySchedules;
    }

    @Override
    public Optional<? extends Entity> create(Object singleDayClasses, Entity scheduleReference) {
        if (!(singleDayClasses instanceof Pair || scheduleReference instanceof Schedule)) {
            throw new IllegalArgumentException();
        }

        Pair<Pair<Integer, LocalDate>, List<String>> mapElement = (Pair<Pair<Integer, LocalDate>, List<String>>) singleDayClasses;
        GroupedDailySchedule groupedDailySchedule = new GroupedDailySchedule();
        groupedDailySchedule.setId(null);
        groupedDailySchedule.setGroupId(mapElement.getKey().getKey());
        groupedDailySchedule.setDate(mapElement.getKey().getValue());
        groupedDailySchedule.setSchedule((Schedule) scheduleReference);
        groupedDailySchedule.setClassDetails((List<ClassDetails>) preparedClassDetailsFactory.create(mapElement.getValue(), groupedDailySchedule));
        
        return Optional.of(groupedDailySchedule);
    }
}
