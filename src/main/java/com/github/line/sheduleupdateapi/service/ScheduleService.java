/*
package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.ClassDetailsRepository;
import com.github.line.sheduleupdateapi.repository.GroupedDailyScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;

import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ScheduleVersionRepository scheduleVersionRepository;
    private final GroupedDailyScheduleRepository groupedDailyScheduleRepository;
    private final ClassDetailsRepository classDetailsRepository;

    private ScheduleService() {
        throw new AssertionError();
    }

    public ScheduleService(ScheduleRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void save(Schedule schedule) {
        List<GroupedDailySchedule> groupedDailySchedules = schedule.getDailySchedule();
        List<ClassDetails> classDetails = groupedDailySchedules
                .stream()
                .map(groupedDailySchedule -> { return groupedDailySchedule.getClassDetails();})
                .collect(toUnmofii);
    }

    private void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    private void saveScheduleVersion(ScheduleVersion scheduleVersion) {
        scheduleVersionRepository.save(scheduleVersion);
    }

    private void saveGroupedDailySchedules(List<GroupedDailySchedule> groupedDailySchedules) {
        groupedDailyScheduleRepository.saveAll(groupedDailySchedules);
    }

    private void saveClassDetails(List<ClassDetails> classDetails) {
        classDetailsRepository.saveAll(classDetails);
    }

}
*/