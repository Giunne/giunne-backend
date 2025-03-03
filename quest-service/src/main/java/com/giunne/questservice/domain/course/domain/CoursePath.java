package com.giunne.questservice.domain.course.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CoursePath {
    private Long id;
    private Course parents;
    private Course child;
}
