package com.giunne.questservice.domain.course.repository.jpa;

import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCourseRepository extends JpaRepository<CourseEntity, Long> {
    List<CourseEntity> findByRoadMap_Id(Long roadMapId);
}
