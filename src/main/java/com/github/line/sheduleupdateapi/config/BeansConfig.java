package com.github.line.sheduleupdateapi.config;

import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.service.*;
import com.github.line.sheduleupdateapi.utils.UrlInputStreamFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class BeansConfig {

    //repositories
    @Resource
    public ScheduleVersionRepository scheduleVersionRepository;
    @Resource
    public ScheduleRepository scheduleRepository;

    //components
    @Bean
    public UrlInputStreamFetcher urlInputStreamFetcher() {
        return new UrlInputStreamFetcher();
    }

    //services
    @Bean
    public ScheduleService scheduleService() {
        return new ScheduleService(urlInputStreamFetcher(), scheduleRepository);
    }
    @Bean
    public ScheduleVersionService scheduleVersionService() {
        return new ScheduleVersionService(scheduleVersionRepository);
    }

    public final List<Observer> observers() {
        List<Observer> observers = new ArrayList<>();
        observers.add(scheduleVersionService());
        observers.add(scheduleService());

        return Collections.unmodifiableList(observers);
    }

    @Bean
    public ScheduledVersionTracker scheduledVersionTrackerService() {
        return new ScheduledVersionTracker(observers(), scheduleVersionRepository);
    }
}
