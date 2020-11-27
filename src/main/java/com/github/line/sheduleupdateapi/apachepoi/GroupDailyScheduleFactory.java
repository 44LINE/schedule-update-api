package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class GroupDailyScheduleFactory implements EntityFactory<GroupedDailySchedule, Schedule, Pair<Pair<Integer, LocalDate>, List<String>>> {
    private final EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> factory;

    public GroupDailyScheduleFactory(@Autowired EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> factory) {
        this.factory = factory;
    }

    @Override
    public Optional<GroupedDailySchedule> create(Schedule schedule, Pair<Pair<Integer, LocalDate>, List<String>> singleDayClasses) {
        GroupedDailySchedule groupedDailySchedule = new GroupedDailySchedule();
        groupedDailySchedule.setId(null);
        groupedDailySchedule.setGroupId(singleDayClasses.getKey().getKey());
        groupedDailySchedule.setDate(singleDayClasses.getKey().getValue());
        groupedDailySchedule.setSchedule(schedule);
        groupedDailySchedule.setClassDetails(factory.createCollection(groupedDailySchedule, singleDayClasses.getValue()));

        return Optional.of(groupedDailySchedule);
    }
}
