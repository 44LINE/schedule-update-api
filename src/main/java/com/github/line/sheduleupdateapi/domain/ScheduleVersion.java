package com.github.line.sheduleupdateapi.domain;

import com.github.line.sheduleupdateapi.service.EntityType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name = "schedule_versions", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "url", "addition_date"}))
public class ScheduleVersion implements EntityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    @NotNull
    private String url;

    @Column(name = "update_date")
    @NotNull
    private LocalDateTime updateDate;

    @Column(name = "addition_date")
    @NotNull
    private LocalDateTime additionDate;

    @OneToOne(mappedBy = "scheduleVersion")
    private Optional<? extends Entity> schedule;

    public ScheduleVersion() {
    }

    public ScheduleVersion(Long id, @NotNull String url, @NotNull LocalDateTime updateDate, @NotNull LocalDateTime additionDate, Optional<? extends Entity> schedule) {
        this.id = id;
        this.url = url;
        this.updateDate = updateDate;
        this.additionDate = additionDate;
        this.schedule = schedule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getAdditionDate() {
        return additionDate;
    }

    public void setAdditionDate(LocalDateTime additionDate) {
        this.additionDate = additionDate;
    }

    public Optional<? extends Entity> getSchedule() {
        return schedule;
    }

    public void setSchedule(Optional<? extends Entity> schedule) {
        this.schedule = schedule;
    }
}
