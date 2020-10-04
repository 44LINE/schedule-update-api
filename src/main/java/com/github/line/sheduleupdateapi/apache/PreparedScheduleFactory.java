package com.github.line.sheduleupdateapi.apache;

import com.github.line.sheduleupdateapi.domain.Schedule;
import com.github.line.sheduleupdateapi.service.PreparedEntityFactory;

import javax.persistence.Entity;

public class PreparedScheduleFactory implements PreparedEntityFactory {

    
    public PreparedScheduleFactory() {
    }

    @Override
    public Schedule create() {
        return null;
    }
}
