package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;

import javax.persistence.Entity;
import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleUpdateListener implements Observer{

    //private final ScheduleService scheduleService;
    private final PreparedEntityFactory preparedScheduleFactory;

    private ScheduleUpdateListener() {
        throw new AssertionError();
    }

    public ScheduleUpdateListener(//ScheduleService scheduleService,
                                  PreparedEntityFactory preparedScheduleFactory) {
        //this.scheduleService = scheduleService;
        System.out.println("listener constr");
        this.preparedScheduleFactory = preparedScheduleFactory;
    }

    @Override
    @Transactional
    public void update() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();

        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            if (date.isPresent()) {
                //
                System.out.println(date.get().toString());
                //
                ScheduleVersion scheduleVersion = new ScheduleVersion();
                Optional<? extends Entity> schedule = preparedScheduleFactory.create(url.get(), scheduleVersion);
                if (schedule.isPresent()) {
                    scheduleVersion.setId(null);
                    scheduleVersion.setUrl(url.get().getPath());
                    scheduleVersion.setUpdateDate(date.get());
                    //
                    System.out.println(url.toString() + " " + LocalDateTime.now().toString());
                    //
                    scheduleVersion.setSchedule((Schedule) schedule.get());
                    scheduleVersion.setAdditionDate(LocalDateTime.now());


                    //scheduleService.todo
                }
            }
        }
    }


}
