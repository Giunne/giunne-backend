package com.giunne.questservice.domain.coursePosition.domain;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.course.domain.type.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CoursePosition {
    private Long id;
    public Course course;
    private Position position;
}
