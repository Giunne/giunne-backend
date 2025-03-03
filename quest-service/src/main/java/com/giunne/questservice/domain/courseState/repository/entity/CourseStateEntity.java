package com.giunne.questservice.domain.courseState.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.courseState.domain.CourseState;
import com.giunne.questservice.domain.courseState.domain.type.CourseProgress;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_state")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseStateEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_state_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no", nullable = true)
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_no")
    private CourseEntity course; // 코스

    @Enumerated(EnumType.STRING)
    @Column(name = "course_progress", nullable = false)
    private CourseProgress courseProgress;

    @Embedded
    private Active isActive = Active.from(true);

    public CourseStateEntity(CourseState courseState) {
        this.id = courseState.getId();
        this.player = new PlayerEntity(courseState.getPlayer());
        this.course = new CourseEntity(courseState.getCourse());
        this.courseProgress = courseState.getCourseProgress();
    }

    public CourseState toCourseState() {
        return CourseState.builder()
                .id(id)
                .player(player.toPlayer())
                .course(course.toCourse())
                .courseProgress(courseProgress)
                .build();
    }

}
