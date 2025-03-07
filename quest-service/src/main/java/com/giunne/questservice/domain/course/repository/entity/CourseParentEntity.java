package com.giunne.questservice.domain.course.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.CourseParent;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "course_parent")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseParentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_parent_no")
    private Long id; // 코스 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "node_id", nullable = false)
    private CourseEntity node;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id", nullable = false)
    private CourseEntity parents;


    public CourseParentEntity(CourseParent courseParent) {
        this.id = courseParent.getId();
        this.node = new CourseEntity(courseParent.getNode());
        this.parents = new CourseEntity(courseParent.getParents());
    }

    public CourseParent toCourseParent() {
        return CourseParent.builder()
                .id(id)
                .node(node.toCourse())
                .parents(parents.toCourse())
                .build();
    }
}
