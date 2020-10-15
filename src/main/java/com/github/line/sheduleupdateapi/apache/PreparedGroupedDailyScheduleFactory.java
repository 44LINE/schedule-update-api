package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import javafx.util.Pair;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.tomcat.jni.Local;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

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
        if (!(collection instanceof Map || scheduleReference instanceof Schedule)) {
            throw new IllegalArgumentException();
        }

        Map<Integer, List<String>> columnsContent = (Map<Integer, List<String>>) collection;
        Map<Pair<Integer, LocalDate>, List<String>> splittedColumnsContentByDate = new HashMap<>();
        List<GroupedDailySchedule> groupedDailySchedules = new ArrayList<>();
        LocalDate dateOfSingleScheduleDay = CustomFixedIndexes.INITIAL_DATE;

        for (Integer groupId: columnsContent.keySet()) {
            for (Iterator<String> it = columnsContent.get(groupId).iterator(); it.hasNext(); ) {
                String cellContent = it.next();
                //kiedy napotka 21 lub 22 w komorce
                if (cellContent.matches("21") || cellContent.matches("22")) {
                    Pair<Integer, LocalDate> groupAndDate = new Pair<>(groupId, dateOfSingleScheduleDay);
                    List<String> dailyClasses = new ArrayList<>();

                    for (int rowId = 0; rowId < 12; rowId++) {
                        dailyClasses.add(it.next());
                    }

                    splittedColumnsContentByDate.put(groupAndDate, dailyClasses);
                }
            }
        }

        for ( Pair<Integer, LocalDate> groupAndDate: splittedColumnsContentByDate.keySet()) {
            Pair<Pair<Integer, LocalDate>, List<String>> mapElement = new Pair<>(groupAndDate, splittedColumnsContentByDate.get(groupAndDate));
            groupedDailySchedules.add((GroupedDailySchedule) create(mapElement, scheduleReference)
                    .orElseThrow(IllegalStateException::new));
        }

        return groupedDailySchedules;
    }

    @Override
    public Optional<? extends Entity> create(Object singleDayClasses, Entity scheduleReference) {
        if (!(singleDayClasses instanceof List || scheduleReference instanceof Schedule)) {
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
