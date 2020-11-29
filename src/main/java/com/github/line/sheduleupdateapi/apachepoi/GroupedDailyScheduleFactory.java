package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.apachepoi.container.SingleDayClasses;
import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class GroupedDailyScheduleFactory implements EntityFactory<GroupedDailySchedule, Schedule, SingleDayClasses> {
    private final EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> factory;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public GroupedDailyScheduleFactory(@Autowired EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> factory) {
        this.factory = factory;
    }

    @Override
    public Optional<GroupedDailySchedule> create(Schedule schedule, SingleDayClasses singleDayClasses) {
        GroupedDailySchedule groupedDailySchedule = new GroupedDailySchedule();
        groupedDailySchedule.setId(null);
        groupedDailySchedule.setGroupId(singleDayClasses.getGroupId());
        groupedDailySchedule.setDate(singleDayClasses.getDate());
        groupedDailySchedule.setSchedule(schedule);
        groupedDailySchedule.setClassDetails(factory.createCollection(groupedDailySchedule, singleDayClasses.getContent()));
        return Optional.of(groupedDailySchedule);
    }
}
