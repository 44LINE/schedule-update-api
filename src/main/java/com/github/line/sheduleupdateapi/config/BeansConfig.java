package com.github.line.sheduleupdateapi.config;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
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
    public ScheduleRepository scheduleRepository;
    @Resource
    public ScheduleVersionRepository scheduleVersionRepository;

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

    public final List<Observer> observers() {
        List<Observer> observers = new ArrayList<>();

        return Collections.unmodifiableList(observers);
    }

    @Bean
    public ScheduledVersionTracker scheduledVersionTrackerService() {
        return new ScheduledVersionTracker(observers(), scheduleVersionRepository);
    }
}
