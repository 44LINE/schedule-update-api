package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleUpdateListener implements Observer{

    private final ScheduleService scheduleService;
    private final PreparedEntityFactory preparedEntityFactory;

    private ScheduleUpdateListener() {
        throw new AssertionError();
    }

    public ScheduleUpdateListener(ScheduleService scheduleService, PreparedEntityFactory preparedEntityFactory) {
        this.scheduleService = scheduleService;
        this.preparedEntityFactory = preparedEntityFactory;
    }

    @Override
    @Transactional
    public void update() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();

        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            if (date.isPresent()) {
                ScheduleVersion scheduleVersion = new ScheduleVersion();
                Optional<? extends Entity> schedule = preparedEntityFactory.create(url.get(), scheduleVersion);
                if (schedule.isPresent()) {
                    scheduleVersion.setId(null);
                    scheduleVersion.setUrl(url.get().getPath());
                    scheduleVersion.setUpdateDate(date.get());
                    scheduleVersion.setSchedule(schedule);
                    scheduleVersion.setAdditionDate(LocalDateTime.now());

                    //scheduleService.todo
                }
            }
        }
    }


}
