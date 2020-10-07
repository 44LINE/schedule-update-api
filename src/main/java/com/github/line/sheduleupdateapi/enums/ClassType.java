package com.github.line.sheduleupdateapi.enums;

public enum ClassType {
    EXERCISE,
    LAB,
    LECTURE,
    FOREIGN_LANGUAGE;

    private static final String LECTURE_CHAR_SEQUENCE_0 = "wykład";
    private static final String FOREIGN_LANGUAGE_CHAR_SEQUENCE_0 = "angielski";
    private static final String LAB_CHAR_SEQUENCE_0 = "lab";
    private static final String EXERCISE_CHAR_SEQUENCE_0 = "ćwiczenia";

    public static ClassType retrieveClassType(String rowValue) {

        if (rowValue.contains(EXERCISE_CHAR_SEQUENCE_0)) {
            return ClassType.EXERCISE;
        } else if (rowValue.contains(FOREIGN_LANGUAGE_CHAR_SEQUENCE_0)) {
            return ClassType.FOREIGN_LANGUAGE;
        } else if (rowValue.contains(LAB_CHAR_SEQUENCE_0)) {
            return ClassType.LAB;
        } else {
            return ClassType.LECTURE;
        }
    }
}
