package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.repository.ClassDetailsRepository;
import com.github.line.sheduleupdateapi.repository.GroupedDailyScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;

import javax.transaction.Transactional;

public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ScheduleVersionRepository scheduleVersionRepository;
    private final GroupedDailyScheduleRepository groupedDailyScheduleRepository;
    private final ClassDetailsRepository classDetailsRepository;

    private ScheduleService() {
        throw new AssertionError();
    }

    public ScheduleService(ScheduleRepository scheduleRepository,
                           ScheduleVersionRepository scheduleVersionRepository,
                           GroupedDailyScheduleRepository groupedDailyScheduleRepository,
                           ClassDetailsRepository classDetailsRepository) {
        this.scheduleRepository = scheduleRepository;
        this.scheduleVersionRepository = scheduleVersionRepository;
        this.groupedDailyScheduleRepository = groupedDailyScheduleRepository;
        this.classDetailsRepository = classDetailsRepository;
    }

    @Transactional
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
        groupedDailyScheduleRepository.saveAll(schedule.getDailySchedule());
        for (GroupedDailySchedule gr : schedule.getDailySchedule()) {
            classDetailsRepository.saveAll(gr.getClassDetails());
        }
    }
}
