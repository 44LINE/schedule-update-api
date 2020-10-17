package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Schedule;

import javax.transaction.Transactional;

public class ScheduleUpdateListener implements Observer{

    private final ScheduleService scheduleService;
    private final ScheduleVersionService scheduleVersionService;
    private final ScheduleUpdateHandler scheduleUpdateHandler;

    private ScheduleUpdateListener() {
        throw new AssertionError();
    }

    public ScheduleUpdateListener(ScheduleService scheduleService,
                                  ScheduleVersionService scheduleVersionService,
                                  ScheduleUpdateHandler scheduleUpdateHandler) {
        this.scheduleService = scheduleService;
        this.scheduleVersionService = scheduleVersionService;
        this.scheduleUpdateHandler = scheduleUpdateHandler;
    }

    @Override
    @Transactional
    public void update() {
        Schedule handle = scheduleUpdateHandler.handle()
                .orElseThrow(UnknownError::new);
        scheduleService.save(handle);
    }


}
