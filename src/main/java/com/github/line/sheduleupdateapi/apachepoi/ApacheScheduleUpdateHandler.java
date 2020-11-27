package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.service.ScheduleUpdateHandler;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class ApacheScheduleUpdateHandler implements ScheduleUpdateHandler {
    private final EntityFactory<Schedule, ScheduleVersion, URL> scheduleFactory;

    public ApacheScheduleUpdateHandler(@Autowired EntityFactory<Schedule, ScheduleVersion, URL> scheduleFactory) {
        this.scheduleFactory = scheduleFactory;
    }

    @Override
    public Optional<Schedule> handle() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();
        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            if (date.isPresent()) {
                ScheduleVersion scheduleVersion = new ScheduleVersion();
                Optional<Schedule> schedule = scheduleFactory.create(scheduleVersion, url.get());
                if (schedule.isPresent()) {
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
