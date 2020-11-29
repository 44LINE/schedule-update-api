package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.apachepoi.container.SheetContentHolder;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.TemporaryFile;
import com.github.line.sheduleupdateapi.utils.UrlInputStreamFetcher;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.github.line.sheduleupdateapi.apachepoi.CustomFixedIndexes.*;
import static com.github.line.sheduleupdateapi.apachepoi.WorkbookHelper.*;

@Component
public class ScheduleFactory implements EntityFactory<Schedule, ScheduleVersion, URL> {
    private final EntityCollectionFactory<GroupedDailySchedule, Schedule, List<String>> factory;

    public ScheduleFactory(@Autowired EntityCollectionFactory<GroupedDailySchedule, Schedule, List<String>> factory) {
        this.factory = factory;
    }
    @Override
    public Optional<Schedule> create(ScheduleVersion scheduleVersion, URL url) {
            Optional<List<List<String>>> preparedCollections = fetchAndPrepareData(url);
            if (preparedCollections.isPresent()) {
                Schedule schedule = new Schedule();
                schedule.setLatest(true);
                schedule.setScheduleVersion(scheduleVersion);
                schedule.setDailySchedule(factory.createCollection(schedule, preparedCollections.get()));
                return Optional.of(schedule);
            }
        return Optional.empty();
    }

    private Optional<List<List<String>>> fetchAndPrepareData(URL url) {
        Optional<InputStream> inputStream = UrlInputStreamFetcher.fetch(url);
        if (inputStream.isPresent()) {
            Optional<File> file = TemporaryFile.writeInputStreamToFile(inputStream.get());
            if (file.isPresent()) {
                Optional<Sheet> sheet = getSheetFromXlsFile(file.get(), DEFAULT_SHEET_INDEX);
                if (sheet.isPresent()) {
                    return getGroupedColumns(sheet.get());
                }
            }
        }
        return Optional.empty();
    }

    private Optional<List<List<String>>> getGroupedColumns(Sheet sheet) {
        SheetContentHolder sheetContentHolder = new SheetContentHolder();
        for (int index = GROUP_ONE_COLUMN_INDEX, groupId = 0; index<= GROUP_FOUR_COLUMN_INDEX; index++, groupId++) {
            sheetContentHolder.addColumnContent(getAddressAndRangeMergedCellsInColumn(sheet, index),
                                                getCellContent(getColumnFromSheet(sheet, index)),
                                                groupId);
        }
        return Optional.of(splitMergedCells(sheetContentHolder));
    }
}
