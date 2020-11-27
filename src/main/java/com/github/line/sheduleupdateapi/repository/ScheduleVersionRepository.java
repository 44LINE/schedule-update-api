package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.ScheduleVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ScheduleVersionRepository extends JpaRepository<ScheduleVersion, Long> {

    LocalDateTime getUpdateDateById(Long id);
    String getUrlById(Long id);

    @Query("SELECT d.updateDate FROM ScheduleVersion AS d WHERE d.schedule.isLatest = true")
    LocalDateTime getLatestUpdateDate();
}
