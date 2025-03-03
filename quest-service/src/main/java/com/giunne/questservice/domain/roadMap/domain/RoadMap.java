package com.giunne.questservice.domain.roadMap.domain;

import com.giunne.questservice.domain.roadMap.domain.type.Description;
import com.giunne.questservice.domain.roadMap.domain.type.RoadMapType;
import com.giunne.questservice.domain.roadMap.domain.type.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RoadMap {
    private Long id; // 카테고리
    private Long recreationNo; // 레크레이션
    private Title title; // 제목
    private Description description; // 설명
    RoadMapType roadMapType;
}
