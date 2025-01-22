package com.giunne.memberservice.domain.school.repository.jpa;

import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface JpaSchoolRepository extends JpaRepository<SchoolEntity, Long> {

    Optional<SchoolEntity> findBySchoolId_SchoolId (String schoolId);
}
