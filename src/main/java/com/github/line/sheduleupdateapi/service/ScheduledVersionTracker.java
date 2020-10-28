package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.exceptions.DateExtractionException;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;
import jdk.nashorn.internal.ir.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Immutable
public class ScheduledVersionTracker implements Observable{

    private final List<Observer> observers;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
    private final ScheduleVersionRepository scheduleVersionRepository;

    private ScheduledVersionTracker() {
        throw new AssertionError();
    }

    public ScheduledVersionTracker(List<Observer> observers, ScheduleVersionRepository scheduleVersionRepository) {
        this.observers = observers;
        this.scheduleVersionRepository = scheduleVersionRepository;
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update();
        }
    }

    public void enable() {
        scheduler.scheduleWithFixedDelay(track, 30, 30, TimeUnit.SECONDS);
    }

    void disable() {
        scheduler.shutdown();
    }

    private final Runnable track = new Runnable() {
        public void run() {
            System.out.println("Tracking run.");
            Long count = scheduleVersionRepository.count();
            if (count == 0L) {
                notifyObservers();
            } else {
                LocalDateTime extractedDate = CustomExtractor.extractLatestUpdateDate()
                        .orElseThrow(DateExtractionException::new);

                LocalDateTime latestDate = scheduleVersionRepository.getUpdateDateById(count);

                if (extractedDate.isAfter(latestDate)) {
                    notifyObservers();
                }
            }
        }
    };

}
