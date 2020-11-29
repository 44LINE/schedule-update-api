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
import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ClassDetailsCollectionFactory implements EntityCollectionFactory<ClassDetails, GroupedDailySchedule, String> {
    private final EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> factory;
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    public ClassDetailsCollectionFactory(@Autowired EntityFactory<ClassDetails, GroupedDailySchedule, Pair<Integer, String>> factory) {
        this.factory = factory;
    }

    @Override
    public List<ClassDetails> createCollection(GroupedDailySchedule reference, List<String> collection) {
        List<ClassDetails> classDetailsList = new ArrayList<>();
        for (int i = 0; i < collection.size(); i++) {
            Pair<Integer, String> rowIdCellContent = new Pair<>(Integer.valueOf(i), collection.get(i));
            Optional<ClassDetails> classDetails = factory.create(reference, rowIdCellContent);
            if (classDetails.isPresent()) {
                classDetailsList.add(classDetails.get());
                i+=2;
            }
        }
        return classDetailsList;
    }
}
