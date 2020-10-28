package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.ClassObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassObjectRepository extends JpaRepository<ClassObject, Long> {
}
