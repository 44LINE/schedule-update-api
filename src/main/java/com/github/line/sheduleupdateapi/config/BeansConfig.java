package com.github.line.sheduleupdateapi.config;

import com.github.line.sheduleupdateapi.apache.FixedRowMapper;
import com.github.line.sheduleupdateapi.apache.PreparedClassDetailsFactory;
import com.github.line.sheduleupdateapi.apache.PreparedGroupedDailyScheduleFactory;
import com.github.line.sheduleupdateapi.apache.PreparedScheduleFactory;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.ClassObjectRepository;
import com.github.line.sheduleupdateapi.repository.LecturerRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
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
    public ScheduleUpdateListener scheduleUpdateListener() {
        return new ScheduleUpdateListener(preparedScheduleFactory());
    };

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
