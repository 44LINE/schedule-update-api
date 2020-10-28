package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import org.hibernate.annotations.Cache;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository("scheduleVersionRepository")
public interface ScheduleVersionRepository extends JpaRepository<ScheduleVersion, Long> {

    LocalDateTime getUpdateDateById(Long id);
    String getUrlById(Long id);
}
