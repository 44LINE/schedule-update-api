package com.github.line.sheduleupdateapi.enums;

import java.time.LocalTime;

public enum DayTimePeriods {

    MORNING(LocalTime.of(8, 0), LocalTime.of(10, 30)),
    MIDDAY(LocalTime.of(10, 45), LocalTime.of(13, 15)),
    AFTERNOON(LocalTime.of(14, 0), LocalTime.of(16, 30)),
    EVENING(LocalTime.of(16, 45), LocalTime.of(19, 15));

    LocalTime startTime;
    LocalTime endTime;

    DayTimePeriods(LocalTime startTime, LocalTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
