package com.github.line.sheduleupdateapi.apachepoi.container;

import java.time.LocalDate;
import java.util.List;

public class SingleDayClasses {
    private final int groupId;
    private final LocalDate date;
    private final List<String> content;

    public SingleDayClasses(int groupId, LocalDate date, List<String> content) {
        this.groupId = groupId;
        this.date = date;
        this.content = content;
    }

    public int getGroupId() {
        return groupId;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<String> getContent() {
        return content;
    }
}
