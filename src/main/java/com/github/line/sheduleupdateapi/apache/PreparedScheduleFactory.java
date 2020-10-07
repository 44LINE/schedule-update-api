package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.AssertionFailure;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class PreparedScheduleFactory implements PreparedEntityFactory {
    private final PreparedGroupedDailyScheduleFactory preparedGroupedDailyScheduleFactory;

    private PreparedScheduleFactory() {
        throw new AssertionError();
    }

    public PreparedScheduleFactory(PreparedGroupedDailyScheduleFactory preparedGroupedDailyScheduleFactory) {
        this.preparedGroupedDailyScheduleFactory = preparedGroupedDailyScheduleFactory;
    }

    @Override
    public Iterable<? extends Entity> create(Collection<?> collection, Entity selfReference) {
        return null;
    }


    @Override
    public Optional<? extends Entity> create(Object argument, Entity selfReference) {
        if (!(argument instanceof Workbook || selfReference instanceof ScheduleVersion)) {
            throw new IllegalArgumentException();
        }

        Schedule schedule = new Schedule();
        schedule.setLatest(true);
        schedule.setScheduleVersion((ScheduleVersion) selfReference);

        Workbook workbook =
        create()
        schedule.setDailySchedule();

        return Optional.empty();
    }
}
