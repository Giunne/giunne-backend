package com.giunne.questservice.domain.roadMap.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import com.giunne.questservice.domain.roadMap.domain.type.Description;
import com.giunne.questservice.domain.roadMap.domain.type.RoadMapType;
import com.giunne.questservice.domain.roadMap.domain.type.Title;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "road_map")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadMapEntity extends BaseEntity {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "roadmap_type", nullable = false)
    RoadMapType roadMapType;

    public RoadMapEntity(RoadMap roadMap){
        this.id = roadMap.getId();
        this.recreationNo = roadMap.getRecreationNo();
        this.title = roadMap.getTitle();
        this.description = roadMap.getDescription();
        this.roadMapType = roadMap.getRoadMapType();
    }

    public RoadMap toRoadMap(){
        return RoadMap.builder()
                .id(id)
                .recreationNo(recreationNo)
                .title(title)
                .description(description)
                .roadMapType(roadMapType)
                .build();
    }

}
