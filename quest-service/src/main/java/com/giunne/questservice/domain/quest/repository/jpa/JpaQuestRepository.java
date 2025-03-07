package com.giunne.questservice.domain.quest.repository.jpa;

import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaQuestRepository extends JpaRepository<QuestEntity, Long> {
    List<QuestEntity> findByCourse_Id(Long courseId);

    @Query("SELECT i FROM QuestEntity i WHERE i.course.id in (:courseIdList)")
    List<QuestEntity> findByCourseIdList(@Param("courseIdList") List<Long> courseIdList);

    @Query("SELECT i FROM QuestEntity i WHERE i.course in (:courseList)")
    List<QuestEntity> findByCourseList(@Param("courseList") List<CourseEntity> courseList);
}
