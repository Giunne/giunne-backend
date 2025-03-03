package com.giunne.questservice.domain.roadMap.repository.jpa;

import com.giunne.questservice.domain.roadMap.repository.entity.RoadMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaRoadmapRepository extends JpaRepository<RoadMapEntity, Long> {
    List<RoadMapEntity> findByRecreationNo(Long recreationNo);
}
