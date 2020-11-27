package com.github.line.sheduleupdateapi.apachepoi;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import com.github.line.sheduleupdateapi.service.EntityCollectionFactory;
import com.github.line.sheduleupdateapi.service.EntityFactory;
import com.github.line.sheduleupdateapi.utils.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ClassDetailsCollectionFactory implements EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> {
    private final EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> factory;
    private final AtomicInteger rowIndex = new AtomicInteger(0);

    public ClassDetailsCollectionFactory(@Autowired EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> factory) {
        this.factory = factory;
    }

    @Override
    public List<ClassDetails> createCollection(GroupedDailySchedule groupedDailySchedule, List<String> collection) {
        List<ClassDetails> classDetailsList = new ArrayList<>();
        for (String string: collection) {
            factory.create(groupedDailySchedule, new Pair<>(rowIndex.getAndIncrement(), string))
                    .ifPresent(newClassDetails -> {
                        classDetailsList.add(newClassDetails);
                        rowIndex.addAndGet(2);
                    });
        }

        rowIndex.lazySet(0);
        return classDetailsList;
    }
}
