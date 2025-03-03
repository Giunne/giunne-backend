package com.giunne.questservice.domain.coursePosition.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.repository.entity.CourseEntity;
import com.giunne.questservice.domain.coursePosition.domain.CoursePosition;
import com.giunne.questservice.domain.course.domain.type.Position;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "course_position")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoursePositionEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_position_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_no")
    private CourseEntity course;

    @Embedded
    private Position position = Position.of(0.0,0.0); // 위치

    @Embedded
    private Active isActive = Active.from(true);

    public CoursePositionEntity(CoursePosition coursePosition) {
        this.id = coursePosition.getId();
        this.course = new CourseEntity(coursePosition.getCourse());
        this.position = coursePosition.getPosition();
    }

    public CoursePosition toCoursePosition() {
        return CoursePosition.builder()
                .id(id)
                .course(course.toCourse())
                .position(position)
                .build();
    }

}
