package com.giunne.questservice.domain.course.application.interfaces;

import com.giunne.questservice.domain.course.application.dto.request.GetICourseByRoadmapRequestDto;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.CoursePath;

import java.util.List;
import java.util.Map;

public interface CourseRepository {
    Course insertRootCourse(Course node);
    Course insertCourse(Course node, Course parents);
    Course insertCourse(Course node, List<Course> parents);
    void updateQuery(Course node, CoursePath nodePath);
    void insertBetween(Course newNode, Course currentNode);
    void moveWithSubTree(Course target, Course move);
    Course findById(Long id);
    void deleteCourse(Course target);
    Map<Long, List<Course>> getCourses();
    Map<Long, List<Course>> getCoursesByRoadMapId(Long roadMapId);
}
