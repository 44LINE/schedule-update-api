package com.github.line.sheduleupdateapi.domain;

import com.github.line.sheduleupdateapi.enums.DayTimePeriods;

import javax.persistence.Embeddable;
import java.time.LocalTime;

@Embeddable
public class ClassPeriod {

    private LocalTime startTime;
    private LocalTime endTime;

    public ClassPeriod() {}

    public ClassPeriod(DayTimePeriods dayTimePeriod) {
        this.startTime = dayTimePeriod.getStartTime();
        this.endTime = dayTimePeriod.getEndTime();
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
