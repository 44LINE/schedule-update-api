package com.github.line.sheduleupdateapi.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "grouped_daily_schedules", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class GroupedDailySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "group_id")
    @NotNull
    private long groupId;

    @NotNull
    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "groupedDailySchedule")
    private Set<ClassDetails> classDetails;

    @ManyToOne
    @JoinColumn(name = "schedule_id", referencedColumnName = "id", nullable = false)
    private Schedule schedule;

    public GroupedDailySchedule() {
    }

    public GroupedDailySchedule(long id, @NotNull long groupId, @NotNull LocalDate date, Set<ClassDetails> classDetails, Schedule schedule) {
        this.id = id;
        this.groupId = groupId;
        this.date = date;
        this.classDetails = classDetails;
        this.schedule = schedule;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<ClassDetails> getClassDetails() {
        return Collections.unmodifiableSet(classDetails);
    }

    public void setClassDetails(Set<ClassDetails> classDetails) {
        this.classDetails = classDetails;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
