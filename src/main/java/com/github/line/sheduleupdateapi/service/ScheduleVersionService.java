package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleVersionService {
    private final ScheduleVersionRepository scheduleVersionRepository;

    public ScheduleVersionService(@Autowired ScheduleVersionRepository scheduleVersionRepository) {
        this.scheduleVersionRepository = scheduleVersionRepository;
    }

    public ScheduleVersion save(ScheduleVersion scheduleVersion) {
        return scheduleVersionRepository.save(scheduleVersion);
    }
}
