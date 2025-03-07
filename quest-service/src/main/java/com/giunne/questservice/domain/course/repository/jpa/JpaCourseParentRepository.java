package com.giunne.questservice.domain.course.repository.jpa;

import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseParentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCourseParentRepository extends JpaRepository<CourseParentEntity, Long> {
}
