package com.github.line.sheduleupdateapi.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DayTimePeriodsProfileTest {

    @ParameterizedTest
    @MethodSource("data")
    void retrieveDayTimePeriodSwitch_performance(int argument) {
        //given
        int i = argument;

        //when
        long start = System.nanoTime();
        DayTimePeriods.retrieveDayTimePeriodSwitch(i);
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    @ParameterizedTest
    @MethodSource("data")
    void retrieveDayTimePeriod_performance(int argument) {
        //given
        int i = 1;

        //when
        long start = System.nanoTime();
        DayTimePeriods.retrieveDayTimePeriod(i);
        long end = System.nanoTime();
        System.out.println(end - start);
    }

    @Test
    void retrieveDayTimePeriod_argumentLessThanZero_throwsIllegalArgumentException() {
        //given
        int i = -1;

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> DayTimePeriods.retrieveDayTimePeriod(i));
    }

    @Test
    void retrieveDayTimePeriod_argumentMoreThanAvailable_throwsIllegalArgumentException() {
        //given
        int i = 12;

        //then
        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> DayTimePeriods.retrieveDayTimePeriod(i));
    }

    private static Stream<Arguments> data() {
        return Stream.of(
                Arguments.of(8),
                Arguments.of(8),
                Arguments.of(8),
                Arguments.of(8),
                Arguments.of(8),
                Arguments.of(11),
                Arguments.of(4),
                Arguments.of(3),
                Arguments.of(1),
                Arguments.of(0)
        );
    }
}

/*
using switch

2847753

4151

4107

723

3746

3828
 */

/*
using if

2519844


878


948


1514


1141


1189
 */