package com.github.line.sheduleupdateapi.repository;

import com.github.line.sheduleupdateapi.domain.ClassDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDetailsRepository extends JpaRepository<ClassDetails, Long> {
}
