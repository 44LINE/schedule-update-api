package com.github.line.sheduleupdateapi;

import com.github.line.sheduleupdateapi.domain.ClassObject;
import com.github.line.sheduleupdateapi.repository.ClassObjectRepository;
import com.github.line.sheduleupdateapi.repository.ScheduleVersionRepository;
import com.github.line.sheduleupdateapi.service.ScheduledVersionTracker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.internal.SessionFactoryBuilderImpl;
import org.hibernate.hql.internal.ast.util.SessionFactoryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

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
