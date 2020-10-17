package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;

public class ScheduleVersionService {
    private final ScheduleVersionRepository scheduleVersionRepository;

    private ScheduleVersionService() {
        throw new AssertionError();
    }

    public ScheduleVersionService(ScheduleVersionRepository scheduleVersionRepository) {
        this.scheduleVersionRepository = scheduleVersionRepository;
    }

    public ScheduleVersion save(ScheduleVersion scheduleVersion) {
        return scheduleVersionRepository.save(scheduleVersion);
    }
}
