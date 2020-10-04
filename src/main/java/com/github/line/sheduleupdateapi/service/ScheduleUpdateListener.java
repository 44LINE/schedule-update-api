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

    public ScheduleUpdateListener() {
        throw new AssertionError();
    }

    @Override
    @Transactional
    public void update() {
        Optional<URL> url = CustomExtractor.extractDownloadUrl();

        if (url.isPresent()) {
            Optional<LocalDateTime> date = CustomExtractor.extractLatestUpdateDate();
            Optional<Workbook> workbook = new WorkbookFetcher().fetchWorkbook(url);

            if (date.isPresent() && workbook.isPresent()) {
                //parsing to schedule
            }
        }
    }


}
