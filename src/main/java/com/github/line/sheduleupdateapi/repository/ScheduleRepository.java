package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
