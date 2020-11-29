package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("UPDATE Schedule AS s SET s.isLatest = false")
    void deprecateLatestSchedule();
}
