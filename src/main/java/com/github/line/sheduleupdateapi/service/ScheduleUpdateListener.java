package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.apache.WorkbookFetcher;
import com.github.line.sheduleupdateapi.utils.CustomExtractor;
import org.apache.poi.ss.usermodel.Workbook;

import javax.transaction.Transactional;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;

public class ScheduleUpdateListener implements Observer{

    private final ScheduleService scheduleService;
    private final WorkbookFetcher workbookFetcher;

    public ScheduleUpdateListener() {
        throw new AssertionError();
    }

    public ScheduleUpdateListener(ScheduleService scheduleService, WorkbookFetcher workbookFetcher) {
        this.scheduleService = scheduleService;
        this.workbookFetcher = workbookFetcher;
    }

    @Override
    @Transactional
    public void update() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();

        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            Optional<Workbook> workbook = workbookFetcher.fetchWorkbook(url);

            if (date.isPresent() && workbook.isPresent()) {
                //parsing to schedule
            }
        }
    }


}
