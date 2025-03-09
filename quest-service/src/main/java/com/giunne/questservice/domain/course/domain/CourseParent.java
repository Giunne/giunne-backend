package com.giunne.questservice.domain.course.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CourseParent {
    private Long id;
    private Course node;
    private Course parents;
}
