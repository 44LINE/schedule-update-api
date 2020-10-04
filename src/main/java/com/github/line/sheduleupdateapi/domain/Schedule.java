package com.github.line.sheduleupdateapi.domain;

import com.github.line.sheduleupdateapi.service.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "schedules", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Schedule implements EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "version_id", referencedColumnName = "id", nullable = false)
    private ScheduleVersion scheduleVersion;

    @OneToMany(mappedBy = "schedule")
    @NotNull
    private Set<GroupedDailySchedule> dailySchedule;

    @Column(name = "is_latest")
    @NotNull
    private boolean isLatest;

    public Schedule() {
    }

    public Schedule(long id, ScheduleVersion scheduleVersion, @NotNull Set<GroupedDailySchedule> dailySchedule, @NotNull boolean isLatest) {
        this.id = id;
        this.scheduleVersion = scheduleVersion;
        this.dailySchedule = dailySchedule;
        this.isLatest = isLatest;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ScheduleVersion getScheduleVersion() {
        return scheduleVersion;
    }

    public void setScheduleVersion(ScheduleVersion scheduleVersion) {
        this.scheduleVersion = scheduleVersion;
    }

    public Set<GroupedDailySchedule> getDailySchedule() {
        return Collections.unmodifiableSet(dailySchedule);
    }

    public void setDailySchedule(Set<GroupedDailySchedule> dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setLatest(boolean latest) {
        isLatest = latest;
    }
}
