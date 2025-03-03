package com.giunne.questservice.domain.course.repository.jpa;

import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.course.repository.entity.CoursePathEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaCoursePathRepository extends JpaRepository<CoursePathEntity, Long> {
    List<CoursePathEntity> findAllByChild(CourseEntity course);
    void deleteAllByChild(CourseEntity course);

    @Modifying
    @Query(
            value = "insert into course_path (parents_id,child_id) " +
                    "select sup.parents_id,sub.child_id from course_path sup " +
                    "cross join course_path sub "+
                    "where sup.child_id = :parentsId and sub.parents_id = :childId",
            nativeQuery = true
    )
    void shiftInsertData(@Param("parentsId") Long parentsId, @Param("childId") Long childId);
}
