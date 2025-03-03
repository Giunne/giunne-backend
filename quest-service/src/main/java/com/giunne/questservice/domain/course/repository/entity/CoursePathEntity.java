package com.giunne.questservice.domain.course.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.CoursePath;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course_path",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"parents_id", "child_id"})
        }
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoursePathEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_path_no")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parents_id")
    private CourseEntity parents;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private CourseEntity child;

    @Embedded
    private Active isActive;

    public CoursePathEntity(CoursePath coursePath) {
        this.id = coursePath.getId();
        this.parents = new CourseEntity(coursePath.getParents());
        this.child = new CourseEntity(coursePath.getChild());
    }

    public CoursePath toCoursePath() {
        return CoursePath.builder()
                .id(id)
                .parents(parents.toCourse())
                .child(child.toCourse())
                .build();
    }

}
