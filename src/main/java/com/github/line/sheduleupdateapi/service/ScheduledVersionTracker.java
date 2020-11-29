package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.exceptions.DateExtractionException;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Component
public class ScheduledVersionTracker implements Observable{
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final List<Observer> observers;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ScheduleVersionRepository scheduleVersionRepository;

    public ScheduledVersionTracker(@Autowired List<Observer> observers, @Autowired ScheduleVersionRepository scheduleVersionRepository) {
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
            logger.info("Tracking run.");
            Long count = scheduleVersionRepository.count();
            if (count == 0L) {
                notifyObservers();
            } else {
                logger.info("Versions amount: " + count);
                LocalDateTime extractedDate = CustomExtractor.extractLatestUpdateDate()
                        .orElseThrow(DateExtractionException::new);
                logger.info("Date from source: " + extractedDate);

                LocalDateTime latestDate = scheduleVersionRepository.getLatestUpdateDate();
                logger.info("Latest version update date: " + latestDate);

                if (extractedDate.isAfter(latestDate)) {
                    notifyObservers();
                }
            }
        }
    };

}
