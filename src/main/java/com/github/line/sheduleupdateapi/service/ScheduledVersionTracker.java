package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.exceptions.DateExtractionException;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledVersionTracker implements Observable{

    private final List<Observer> observers;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
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
        scheduler.scheduleWithFixedDelay(track, 0, 15, TimeUnit.MINUTES);
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
                System.out.println("Count: " + count);
                LocalDateTime extractedDate = CustomExtractor.extractLatestUpdateDate()
                        .orElseThrow(DateExtractionException::new);
                System.out.println("Date: " + extractedDate);

                LocalDateTime latestDate = scheduleVersionRepository.getLatestUpdateDate();
                System.out.println("Date from db: " + latestDate);

                if (extractedDate.isAfter(latestDate)) {
                    notifyObservers();
                }
            }
        }
    };

}
