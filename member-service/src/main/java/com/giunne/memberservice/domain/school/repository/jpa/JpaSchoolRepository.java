package com.giunne.memberservice.domain.school.repository.jpa;

import com.giunne.memberservice.domain.school.repository.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface JpaSchoolRepository extends JpaRepository<SchoolEntity, Long> {

    Optional<SchoolEntity> findBySchoolId_SchoolId (String schoolId);

    @Query("SELECT s FROM SchoolEntity s WHERE s.schoolId.schoolId in (:schoolIds)")
    List<SchoolEntity> findBySchoolIdList(@Param("schoolIds") List<String> schoolIdList);

    @Query("SELECT s FROM SchoolEntity s WHERE s.schoolId.schoolId not in (:schoolIds)")
    List<SchoolEntity> findBySchoolIdListNotIn(@Param("schoolIds") List<String> schoolIdList);

    @Modifying
    @Query("UPDATE SchoolEntity s "
            + "SET s.schoolNm.value = :#{#school.schoolNm} "
            + "WHERE s.schoolId.schoolId = :#{#school.schoolId.schoolId}")
    void updateSchool(SchoolEntity school);
}
