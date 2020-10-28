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

    public static DayTimePeriods retrieveDayTimePeriodSwitch(int i) {
        switch (i) {
            case 0: case 1: case 2: {
                return DayTimePeriods.MORNING;
            }
            case 3: case 4: case 5: {
                return DayTimePeriods.MIDDAY;
            }
            case 6: case 7: case 8: {
                return DayTimePeriods.AFTERNOON;
            }
            case 9: case 10: case 11: {
                return DayTimePeriods.EVENING;
            }
            default: {
                throw new IllegalArgumentException();
            }
        }
    }

    public static DayTimePeriods retrieveDayTimePeriod(int i) {
        if (i < 0) {
            throw new IllegalArgumentException();
        } else if (isBetween(i,0, 2)) {
            return DayTimePeriods.MORNING;
        } else if (isBetween(i, 3,5)) {
            return DayTimePeriods.MIDDAY;
        } else if (isBetween(i, 6,8)) {
            return DayTimePeriods.AFTERNOON;
        } else if (isBetween(i, 9,11)) {
            return DayTimePeriods.EVENING;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    private static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
