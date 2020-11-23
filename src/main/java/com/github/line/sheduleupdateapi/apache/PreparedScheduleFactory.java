package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import com.github.line.sheduleupdateapi.utils.TemporaryFile;
import com.github.line.sheduleupdateapi.utils.UrlInputStreamFetcher;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.apache.poi.ss.usermodel.Sheet;

import javax.persistence.Entity;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static com.github.line.sheduleupdateapi.apache.CustomFixedIndexes.*;
import static com.github.line.sheduleupdateapi.apache.WorkbookHelper.*;

public class PreparedScheduleFactory implements PreparedEntityFactory {
    private final PreparedGroupedDailyScheduleFactory preparedGroupedDailyScheduleFactory;

    private PreparedScheduleFactory() {
        throw new AssertionError();
    }

    public PreparedScheduleFactory(PreparedGroupedDailyScheduleFactory preparedGroupedDailyScheduleFactory) {
        this.preparedGroupedDailyScheduleFactory = preparedGroupedDailyScheduleFactory;
    }

    @Override
    public Iterable<? extends Entity> create(Collection<?> columnsContent, Entity schedule) {
        return preparedGroupedDailyScheduleFactory.create(columnsContent, schedule);
    }

    @Override
    public Optional<? extends Entity> create(Object url, Entity scheduleVersion) {
        if (!(url instanceof URL && scheduleVersion instanceof ScheduleVersion)) {
            throw new IllegalArgumentException();
        } else {
            Optional<List<List<String>>> preparedCollections = fetchAndPrepareData((URL) url);
            if (preparedCollections.isPresent()) {

                Schedule schedule = new Schedule();
                schedule.setLatest(true);
                schedule.setScheduleVersion((ScheduleVersion) scheduleVersion);
                schedule.setDailySchedule((List<GroupedDailySchedule>) create(preparedCollections.get(), schedule));
                return Optional.of(schedule);
            }
        }
        return Optional.empty();
    }

    //works
    private Optional<List<List<String>>> fetchAndPrepareData(URL url) {
        Optional<InputStream> inputStream = UrlInputStreamFetcher.fetch(url);
        if (inputStream.isPresent()) {
            Optional<File> file = TemporaryFile.writeInputStreamToFile(inputStream.get());
            if (file.isPresent()) {
                Optional<Sheet> sheet = getSheetFromXlsFile(file.get(), DEFAULT_SHEET_INDEX);
                if (sheet.isPresent()) {
                    return splitCells(sheet.get());
                }
            }
        }
        return Optional.empty();
    }

    //work
    private Optional<List<List<String>>> splitCells(Sheet sheet) {
        List<Pair<Map<Integer, Pair<Integer, Integer>>, List<String>>>  addressRangeContent = new ArrayList<>();

        for (int index = GROUP_ONE_COLUMN_INDEX; index<= GROUP_FOUR_COLUMN_INDEX; index++) {
            addressRangeContent.add(
                    new Pair<>(getAddressAndRangeMergedCellsInColumn(sheet, index),
                               getCellContent(getColumnFromSheet(sheet, index))));
        }

        return Optional.of(splitMergedCells(addressRangeContent));
    }
}
