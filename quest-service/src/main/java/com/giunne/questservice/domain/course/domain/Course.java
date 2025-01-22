package com.giunne.questservice.domain.course.domain;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.type.*;
import com.giunne.questservice.domain.courseAttachment.domain.CourseAttachment;
import com.giunne.questservice.domain.courseClearLog.domain.dmain.CourserClearLog;
import com.giunne.questservice.domain.questCategory.domain.Category;
import com.giunne.questservice.domain.questClearLog.domain.domain.QuestClearLog;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import com.giunne.questservice.domain.section.domain.domain.Section;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "course")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_no")
    private Long id; // 코스 번호

    @Embedded
    private CourseName courseName; // 코스명

    @Embedded
    private Title title; // 제목

    @Embedded
    private Description description; // 설명

    @Embedded
    private Color color = Color.from(0, 0, 0); // 색상

    @Embedded
    private DifficultyLevel difficultyLevel; // 난이도

    @Embedded
    private isTeam isTeam; // 팀전 유무

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CourseAttachment> courseAttachments = new HashSet<>();  // 프로필

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<CourserClearLog> courserClearLogs = new HashSet<>();  // 클리어로그

    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Section> sections = new HashSet<>();  // 클리어로그

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "road_map_no")
    private RoadMap roadMap; // 카테고리

}
