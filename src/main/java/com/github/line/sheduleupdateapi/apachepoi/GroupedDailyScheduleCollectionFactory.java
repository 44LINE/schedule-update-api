package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.apachepoi.container.SingleDayClasses;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.github.line.sheduleupdateapi.apachepoi.CustomFixedIndexes.*;

@Component
public class GroupedDailyScheduleCollectionFactory implements EntityCollectionFactory<GroupedDailySchedule, Schedule, List<String>> {
    private final EntityFactory<GroupedDailySchedule, Schedule, SingleDayClasses> factory;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public GroupedDailyScheduleCollectionFactory(@Autowired EntityFactory<GroupedDailySchedule, Schedule, SingleDayClasses> factory) {
        this.factory = factory;
    }

    @Override
    public List<GroupedDailySchedule> createCollection(Schedule schedule, List<List<String>> columnsContent) {
        return splitByGroupAndDate(columnsContent).stream()
                                           .map(singleDayClasses -> {
                                               return factory.create(schedule, singleDayClasses).get();
                                           })
                                           .collect(Collectors.toList());
    }

    private ConcurrentLinkedQueue<SingleDayClasses> splitByGroupAndDate(List<List<String>> columnsContent) {
        ConcurrentLinkedQueue<SingleDayClasses> queue = new ConcurrentLinkedQueue<>();
        List<String> singleDayContent = new ArrayList<>();

        for (int i = 0; i < columnsContent.size(); i++) {
            int dateIndex = 0;

            //logger.info("Group: " + i);
            for (int j = 0; j < columnsContent.get(i).size(); j++) {
                String singleCellContent = columnsContent.get(i).get(j);
                //logger.info("Row index: " + j + " Content: " + singleCellContent);
                //logger.info("List size: " + singleDayContent.size());
                if (singleDayContent.size() == STATIC_DAILY_ROW_NUMBER) {
                    SingleDayClasses singleDayClasses = new SingleDayClasses(i, getDailyScheduleDate(dateIndex++), singleDayContent);
                    queue.add(singleDayClasses);
                    singleDayContent = new ArrayList<>();
                    if (!isCellSplitter(singleCellContent)) {
                        break;
                    }
                } else if (isCellSplitter(singleCellContent)) {
                    singleDayContent = new ArrayList<>();
                } else {
                    singleDayContent.add(singleCellContent);
                }
            }
        }
        return queue;
    }

    private boolean isCellSplitter(String rowContent) {
        return rowContent.matches(DAILY_SCHEDULE_SPLITTER_0) || rowContent.matches(DAILY_SCHEDULE_SPLITTER_1);
    }
}


