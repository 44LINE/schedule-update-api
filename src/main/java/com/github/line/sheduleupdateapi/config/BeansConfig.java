package com.github.line.sheduleupdateapi.config;

import com.github.line.sheduleupdateapi.apache.*;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.*;
import com.github.line.sheduleupdateapi.service.*;
import com.github.line.sheduleupdateapi.utils.UrlInputStreamFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

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
    @Resource
    public GroupedDailyScheduleRepository groupedDailyScheduleRepository;
    @Resource
    public ClassDetailsRepository classDetailsRepository;
    @Resource
    public LecturerRepository lecturerRepository;
    @Resource
    public ClassObjectRepository classObjectRepository;

    //services
    @Bean
    public LecturerService lecturerService() {
        return new LecturerService(lecturerRepository);
    }
    @Bean
    public ClassObjectService classObjectService() {
        return new ClassObjectService(classObjectRepository);
    }
    @Bean
    public ScheduleVersionService scheduleVersionService() {
        return new ScheduleVersionService(scheduleVersionRepository);
    }
    @Bean
    public ScheduleService scheduleService() {
        return new ScheduleService(scheduleRepository, groupedDailyScheduleRepository, classDetailsRepository);
    }

    //mappers
    @Bean
    public FixedRowMapper fixedRowMapper() {
        return new FixedRowMapper(lecturerService(), classObjectService());
    }

    //implemented factories
    @Bean
    public PreparedClassDetailsFactory preparedClassDetailsFactory() {
        return new PreparedClassDetailsFactory(fixedRowMapper());
    }
    @Bean
    public PreparedGroupedDailyScheduleFactory preparedGroupedDailyScheduleFactory() {
        return new PreparedGroupedDailyScheduleFactory(preparedClassDetailsFactory());
    }
    @Bean
    public PreparedScheduleFactory preparedScheduleFactory() {
        return new PreparedScheduleFactory(preparedGroupedDailyScheduleFactory());
    }

    //components
    @Bean
    public ApacheScheduleUpdateHandler apacheScheduleUpdateHandler() {
        return new ApacheScheduleUpdateHandler(preparedScheduleFactory());
    }
    @Bean
    public ScheduleUpdateListener scheduleUpdateListener() {
        return new ScheduleUpdateListener(scheduleService(), scheduleVersionService(), apacheScheduleUpdateHandler());
    }

    //services
    //@Bean
    //public ScheduleService scheduleService() {
    //    return new ScheduleService(urlInputStreamFetcher(), scheduleRepository);
    //}

    public final List<Observer> observers() {
        List<Observer> observers = new ArrayList<>();
        observers.add(scheduleUpdateListener());

        return Collections.unmodifiableList(observers);
    }

    @Bean
    public ScheduledVersionTracker scheduledVersionTrackerService() {
        return new ScheduledVersionTracker(observers(), scheduleVersionRepository);
    }
}
