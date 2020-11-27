package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.repository.ClassDetailsRepository;
import com.github.line.sheduleupdateapi.repository.GroupedDailyScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final GroupedDailyScheduleRepository groupedDailyScheduleRepository;
    private final ClassDetailsRepository classDetailsRepository;

    public ScheduleService(@Autowired ScheduleRepository scheduleRepository,
                           @Autowired GroupedDailyScheduleRepository groupedDailyScheduleRepository,
                           @Autowired ClassDetailsRepository classDetailsRepository) {
        this.scheduleRepository = scheduleRepository;
        this.groupedDailyScheduleRepository = groupedDailyScheduleRepository;
        this.classDetailsRepository = classDetailsRepository;
    }

    @Transactional
    public void save(Schedule schedule) {
        scheduleRepository.deprecateLatestSchedule();
        scheduleRepository.save(schedule);
        groupedDailyScheduleRepository.saveAll(schedule.getDailySchedule());
        for (GroupedDailySchedule gr : schedule.getDailySchedule()) {
            for (ClassDetails cd :
                    gr.getClassDetails()) {
                System.out.println(cd.toString());
            classDetailsRepository.saveAll(gr.getClassDetails());
            }
        }
    }
}
