package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

import static com.github.line.sheduleupdateapi.apachepoi.CustomFixedIndexes.*;
import static com.github.line.sheduleupdateapi.apachepoi.CustomFixedIndexes.DAILY_SCHEDULE_SPLITTER_1;

@Component
public class GroupedDailyScheduleCollectionFactory implements EntityCollectionFactory<GroupedDailySchedule, Schedule, List<String>> {
    private final EntityFactory<GroupedDailySchedule, Schedule, Pair<Pair<Integer, LocalDate>, List<String>>> factory;

    public GroupedDailyScheduleCollectionFactory(@Autowired EntityFactory<GroupedDailySchedule, Schedule, Pair<Pair<Integer, LocalDate>, List<String>>> factory) {
        this.factory = factory;
    }

    @Override
    public List<GroupedDailySchedule> createCollection(Schedule schedule, List<List<String>> collection) {
        Map<Pair<Integer, LocalDate>, List<String>> splittedColumnsContentByDate = new HashMap<>();
        List<GroupedDailySchedule> groupedDailySchedules = new ArrayList<>();

        for (List<String> columnContent: collection) {
            List<String> dailyClasses = new ArrayList<>();
            int dateIndex = 0;

            for (String rowContent: columnContent) {
                if (dailyClasses.size() == STATIC_DAILY_ROW_NUMBER) {
                    Pair<Integer, LocalDate> groupAndDate = new Pair<>(collection.indexOf(columnContent), getDailyScheduleDate(dateIndex++));
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
            groupedDailySchedules.add(factory.create(schedule, mapElement).get());
        }

        return groupedDailySchedules;
    }
}
