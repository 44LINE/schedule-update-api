package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class ScheduleUpdateListener implements Observer{
    private final ScheduleService scheduleService;
    private final ScheduleVersionService scheduleVersionService;
    private final ScheduleUpdateHandler scheduleUpdateHandler;

    public ScheduleUpdateListener(@Autowired ScheduleService scheduleService,
                                  @Autowired ScheduleVersionService scheduleVersionService,
                                  @Autowired ScheduleUpdateHandler scheduleUpdateHandler) {
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
