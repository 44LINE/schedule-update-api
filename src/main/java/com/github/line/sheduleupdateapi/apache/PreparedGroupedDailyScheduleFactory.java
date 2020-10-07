package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import org.apache.poi.ss.usermodel.Workbook;

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
    public Iterable<? extends Entity> create(Collection<?> queue, Entity selfReference) {
        if (!(queue instanceof Map || selfReference instanceof Schedule)) {
            throw new IllegalArgumentException();
        }
        Map<Integer, LinkedBlockingQueue<String>> columns = (Map<Integer, LinkedBlockingQueue<String>>) queue;

        List<LocalDate> dateColumn = columns.get(0).stream()
                                                    .map(s ->  LocalDate.of()
                                                    .collect(Collectors.toCollection(Collections.unmodifiableList(LinkedList<LocalDate>)));

        HashSet<GroupedDailySchedule> groupedDailySchedules = queue.stream().
    }

    @Override
    public Optional<? extends Entity> create(Object argument, Entity selfReference) {
        if (!(argument instanceof Workbook || selfReference instanceof Schedule)) {
            throw new IllegalArgumentException();
        }



        return Optional.empty();
    }
}
