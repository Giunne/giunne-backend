package com.giunne.questservice.domain.courseState.domain;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.courseState.domain.type.CourseProgress;
import com.giunne.questservice.domain.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CourseState {
    private Long id;
    private Course course;
    private CourseProgress courseProgress;
    private Player player;
}
