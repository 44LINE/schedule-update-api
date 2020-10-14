package com.github.line.sheduleupdateapi.domain;

import com.github.line.sheduleupdateapi.enums.ClassType;
import com.github.line.sheduleupdateapi.service.EntityType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "class_details", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
public class ClassDetails implements EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "class_object_id", referencedColumnName = "id", nullable = false)
    private ClassObject classObject;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "lecturer_id", referencedColumnName = "id", nullable = false)
    private Lecturer lecturer;

    @Column(name = "type")
    //@Enumerated(EnumType.STRING)
    @NotNull
    //@Type(type = "enums.ClassTypePostgres")
    private ClassType type;

    @Embedded
    private ClassPeriod classPeriod;

    @ManyToOne
    @JoinColumn(name = "grouped_daily_schedule_id", referencedColumnName = "id", nullable = false)
    private GroupedDailySchedule groupedDailySchedule;

    public ClassDetails() {}

    public ClassDetails(Long id, ClassObject classObject, Lecturer lecturer, @NotNull ClassType type, ClassPeriod classPeriod, GroupedDailySchedule groupedDailySchedule) {
        this.id = id;
        this.classObject = classObject;
        this.lecturer = lecturer;
        this.type = type;
        this.classPeriod = classPeriod;
        this.groupedDailySchedule = groupedDailySchedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassObject getClassObject() {
        return classObject;
    }

    public void setClassObject(ClassObject classObject) {
        this.classObject = classObject;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public ClassType getType() {
        return type;
    }

    public void setType(ClassType type) {
        this.type = type;
    }

    public ClassPeriod getClassPeriod() {
        return classPeriod;
    }

    public void setClassPeriod(ClassPeriod classPeriod) {
        this.classPeriod = classPeriod;
    }

    public GroupedDailySchedule getGroupedDailySchedule() {
        return groupedDailySchedule;
    }

    public void setGroupedDailySchedule(GroupedDailySchedule groupedDailySchedule) {
        this.groupedDailySchedule = groupedDailySchedule;
    }
}