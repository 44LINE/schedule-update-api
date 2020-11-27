package com.github.line.sheduleupdateapi.domain;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "schedules", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "version_id", referencedColumnName = "id", nullable = false)
    private ScheduleVersion scheduleVersion;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @NotNull
    private List<GroupedDailySchedule> dailySchedule;

    @Column(name = "is_latest")
    @NotNull
    private boolean isLatest;

    public Schedule() {
    }

    public Schedule(long id, ScheduleVersion scheduleVersion, @NotNull List<GroupedDailySchedule> dailySchedule, @NotNull boolean isLatest) {
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

    public List<GroupedDailySchedule> getDailySchedule() {
        return Collections.unmodifiableList(dailySchedule);
    }

    public void setDailySchedule(List<GroupedDailySchedule> dailySchedule) {
        this.dailySchedule = dailySchedule;
    }

    public boolean isLatest() {
        return isLatest;
    }

    public void setLatest(boolean latest) {
        isLatest = latest;
    }
}
