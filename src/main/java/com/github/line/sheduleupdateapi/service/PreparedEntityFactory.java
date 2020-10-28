package com.github.line.sheduleupdateapi.service;

import com.github.line.sheduleupdateapi.domain.ClassPeriod;

import javax.persistence.Entity;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

//
public interface PreparedEntityFactory {
    Iterable<? extends Entity> create(Collection<? extends Object> collection, Entity selfReference);
    //public Iterable<? extends Entity> create(Collection<? extends Object> collection);
    Optional<? extends Entity> create(Object argument, Entity selfReference);
}
