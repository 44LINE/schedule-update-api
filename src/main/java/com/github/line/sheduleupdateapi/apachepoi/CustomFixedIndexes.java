package com.github.line.sheduleupdateapi.apachepoi;

import java.time.LocalDate;

class CustomFixedIndexes {

    final static int DEFAULT_SHEET_INDEX = 0;

    final static int INITIAL_ROW_INDEX = 2;
    final static int FINAL_ROW_INDEX = 391;

    final static String DAILY_SCHEDULE_SPLITTER_0 = "21";
    final static String DAILY_SCHEDULE_SPLITTER_1 = "22";
    final static int STATIC_DAILY_ROW_NUMBER = 12;

    final static int GROUP_ONE_COLUMN_INDEX = 10;
    final static int GROUP_TWO_COLUMN_INDEX = 11;
    final static int GROUP_THREE_COLUMN_INDEX = 12;
    final static int GROUP_FOUR_COLUMN_INDEX = 13;

    private final static LocalDate[] DAILY_SCHEDULE_DATE = {
            LocalDate.of(2020, 10, 3),
            LocalDate.of(2020, 10, 4),
            LocalDate.of(2020, 10, 10),
            LocalDate.of(2020, 10, 11),
            LocalDate.of(2020, 10, 17),
            LocalDate.of(2020, 10, 18),
            LocalDate.of(2020, 10, 24),
            LocalDate.of(2020, 10, 25),
            LocalDate.of(2020, 11, 7),
            LocalDate.of(2020, 11, 8),
            LocalDate.of(2020, 11, 14),
            LocalDate.of(2020, 11, 15),
            LocalDate.of(2020, 11, 21),
            LocalDate.of(2020, 11, 22),
            LocalDate.of(2020, 11, 28),
            LocalDate.of(2020, 11, 29),
            LocalDate.of(2020, 12, 5),
            LocalDate.of(2020, 12, 6),
            LocalDate.of(2020, 12, 12),
            LocalDate.of(2020, 12, 13),
            LocalDate.of(2020, 12, 19),
            LocalDate.of(2020, 12, 20),
            LocalDate.of(2021, 1, 9),
            LocalDate.of(2021, 1, 10),
            LocalDate.of(2021, 1, 16),
            LocalDate.of(2021, 1, 17),
            LocalDate.of(2021, 1, 23),
            LocalDate.of(2021, 1, 24),
            LocalDate.of(2021, 1, 30),
            LocalDate.of(2021, 1, 31)
    };

    private CustomFixedIndexes() {
        throw new AssertionError();
    }

    public static LocalDate getDailyScheduleDate(int index) {
        if (index >=  DAILY_SCHEDULE_DATE.length) {
            return null;
        } else {
            return DAILY_SCHEDULE_DATE[index];
        }
    }
}
