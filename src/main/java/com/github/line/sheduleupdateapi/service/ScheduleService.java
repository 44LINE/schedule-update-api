package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.repository.ScheduleRepository;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;
import com.github.line.sheduleupdateapi.utils.InputStreamFetcher;

import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleService implements Observer{
    private final InputStreamFetcher inputStreamFetcher;
    private final ScheduleRepository repository;

    private ScheduleService() {
        throw new AssertionError();
    }

    public ScheduleService(InputStreamFetcher inputStreamFetcher, ScheduleRepository repository) {
        this.inputStreamFetcher = inputStreamFetcher;
        this.repository = repository;
    }

    @Override
    @Transactional
    public void update() {
        Optional<ScheduleVersion> scheduleVersion = newScheduleVersion();
        if (scheduleVersion.isPresent()) {
            //saving
        }
    }

    private Optional<ScheduleVersion> newScheduleVersion() {
        Optional<URL> extractedUrl = CustomExtractor.extractDownloadUrl();
        Optional<LocalDateTime> extractedDate = CustomExtractor.extractLatestUpdateDate();

        if (extractedUrl.isPresent() && extractedDate.isPresent()) {
            return Optional.of(new ScheduleVersion(
                       null,
                        extractedUrl.get().toString(),
                        extractedDate.get(),
                        LocalDateTime.now(),
                    null
            ));
        } else {
            return Optional.empty();
        }
    }
}
