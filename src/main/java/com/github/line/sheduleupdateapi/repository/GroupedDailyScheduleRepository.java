package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.GroupedDailySchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupedDailyScheduleRepository extends JpaRepository<GroupedDailySchedule, Long> {
}
