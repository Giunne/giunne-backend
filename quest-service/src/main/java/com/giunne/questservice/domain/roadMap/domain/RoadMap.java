package com.giunne.questservice.domain.roadMap.domain;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.api.domain.Quest;
import com.giunne.questservice.domain.roadMap.domain.type.Description;
import com.giunne.questservice.domain.roadMap.domain.type.Title;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "road_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadMap extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "road_map_no")
    private Long id; // 프로필 번호

    @Column(name = "recreation_no")
    private Long recreationNo; // 레크레이션

    @Embedded
    private Title title; // 제목

    @Embedded
    private Description description; // 설명

    @OneToMany(mappedBy = "roadMap", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Course> courses = new HashSet<>();
}
