package com.github.line.sheduleupdateapi.enums;


import java.util.logging.Logger;

public enum ClassType {
    EXERCISE,
    LAB,
    LECTURE,
    FOREIGN_LANGUAGE;

    private static Logger logger = Logger.getLogger(ClassType.class.getName());
    private static final String LECTURE_CHAR_SEQUENCE_0 = "wykład";
    private static final String FOREIGN_LANGUAGE_CHAR_SEQUENCE_0 = "angielski";
    private static final String LAB_CHAR_SEQUENCE_0 = "lab";
    private static final String EXERCISE_CHAR_SEQUENCE_0 = "ćwiczenia";

    public static ClassType retrieveClassType(String rowValue) {
        ClassType classType;

        if (rowValue.contains(EXERCISE_CHAR_SEQUENCE_0)) {
            classType = ClassType.EXERCISE;
        } else if (rowValue.contains(FOREIGN_LANGUAGE_CHAR_SEQUENCE_0)) {
            classType = ClassType.FOREIGN_LANGUAGE;
        } else if (rowValue.contains(LAB_CHAR_SEQUENCE_0)) {
            classType = ClassType.LAB;
        } else {
            classType = ClassType.LECTURE;
        }

        //logger.info("retrieved " + classType.name() + " from: "  + rowValue);
        return classType;
    }
}
