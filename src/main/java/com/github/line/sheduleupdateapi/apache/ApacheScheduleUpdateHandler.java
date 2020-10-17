package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import com.github.line.sheduleupdateapi.service.ScheduleUpdateHandler;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ApacheScheduleUpdateHandler implements ScheduleUpdateHandler {
    private final PreparedEntityFactory preparedScheduleFactory;

    private ApacheScheduleUpdateHandler() {
        throw new AssertionError();
    }

    public ApacheScheduleUpdateHandler(PreparedEntityFactory preparedScheduleFactory) {
        this.preparedScheduleFactory = preparedScheduleFactory;
    }

    @Override
    public Optional<Schedule> handle() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();
        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            if (date.isPresent()) {
                ScheduleVersion scheduleVersion = new ScheduleVersion();
                Optional<Schedule> schedule = (Optional<Schedule>) preparedScheduleFactory.create(url.get(), scheduleVersion);
                if (schedule.isPresent()) {
                    System.out.println("schedule prepared gds size:" + schedule.get().getDailySchedule().size());
                    scheduleVersion.setId(null);
                    scheduleVersion.setUrl(url.get().toString());
                    scheduleVersion.setUpdateDate(date.get());
                    scheduleVersion.setSchedule(schedule.get());
                    scheduleVersion.setAdditionDate(LocalDateTime.now());
                    return schedule;
                }
            }
        }
        return Optional.empty();
    }
}
