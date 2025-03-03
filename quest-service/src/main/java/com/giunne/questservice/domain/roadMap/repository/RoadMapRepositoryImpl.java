package com.giunne.questservice.domain.roadMap.repository;

import com.giunne.questservice.domain.roadMap.application.dto.request.GetIRoadmapRequestDto;
import com.giunne.questservice.domain.roadMap.application.interfaces.RoadMapRepository;
import com.giunne.questservice.domain.roadMap.domain.RoadMap;
import com.giunne.questservice.domain.roadMap.repository.entity.RoadMapEntity;
import com.giunne.questservice.domain.roadMap.repository.jpa.JpaRoadmapRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RoadMapRepositoryImpl implements RoadMapRepository {
    private final JPAQueryFactory queryFactory;
    private final JpaRoadmapRepository roadmapRepository;

    public RoadMap findById(Long id) {
        RoadMapEntity roadMapEntity = roadmapRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 ID입니다.")
        );
        return roadMapEntity.toRoadMap();
    }

    @Override
    public List<RoadMap> findByRecreation(GetIRoadmapRequestDto dto) {
        List<RoadMapEntity> roadMapEntityList = roadmapRepository.findByRecreationNo(dto.getRecreationId());
        return roadMapEntityList.stream().map(RoadMapEntity::toRoadMap).toList();
    }

    @Override
    public List<RoadMap> findAll() {
        List<RoadMapEntity> roadMapEntityList = roadmapRepository.findAll();
        return roadMapEntityList.stream().map(RoadMapEntity::toRoadMap).toList();
    }

    public RoadMap save(RoadMap roadMap) {
        RoadMapEntity roadMapEntity = roadmapRepository.save(new RoadMapEntity(roadMap));
        return roadMapEntity.toRoadMap();
    }

}
