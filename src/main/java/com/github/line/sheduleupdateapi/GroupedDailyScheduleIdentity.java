package com.github.line.sheduleupdateapi;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class GroupedDailyScheduleIdentity implements Serializable {
    @NotNull
    private int groupId;

    @NotNull
    private LocalDate date;

    @NotNull
    private int scheduleVersionId;

    public GroupedDailyScheduleIdentity() {}

    public GroupedDailyScheduleIdentity(@NotNull int groupId, @NotNull LocalDate date, @NotNull int scheduleVersionId) {
        this.groupId = groupId;
        this.date = date;
        this.scheduleVersionId = scheduleVersionId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getScheduleVersionId() {
        return scheduleVersionId;
    }

    public void setScheduleVersionId(int scheduleVersionId) {
        this.scheduleVersionId = scheduleVersionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupedDailyScheduleIdentity that = (GroupedDailyScheduleIdentity) o;
        return groupId == that.groupId &&
                scheduleVersionId == that.scheduleVersionId &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, date, scheduleVersionId);
    }
}
