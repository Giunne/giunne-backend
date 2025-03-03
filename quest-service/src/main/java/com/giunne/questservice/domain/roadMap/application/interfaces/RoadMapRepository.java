package com.giunne.questservice.domain.roadMap.application.interfaces;

import com.giunne.questservice.domain.roadMap.application.dto.request.GetIRoadmapRequestDto;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;

import java.util.List;

public interface RoadMapRepository {
    RoadMap findById(Long id);
    List<RoadMap> findByRecreation(GetIRoadmapRequestDto dto);
    List<RoadMap> findAll();
    RoadMap save(RoadMap roadMap);
}
