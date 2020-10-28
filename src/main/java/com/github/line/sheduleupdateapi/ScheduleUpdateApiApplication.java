package com.github.line.sheduleupdateapi;

import com.github.line.sheduleupdateapi.service.ScheduledVersionTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScheduleUpdateApiApplication {
	public ScheduleUpdateApiApplication(@Autowired ScheduledVersionTracker scheduledVersionTracker)
	{
		scheduledVersionTracker.enable();
	}

	public static void main(String[] args) {
		SpringApplication.run(ScheduleUpdateApiApplication.class, args);
	}


}
