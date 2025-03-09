package com.giunne.questservice.domain.questState.repository.jpa;

import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import com.giunne.questservice.domain.roadMap.repository.entity.RoadMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaQuestStateRepository extends JpaRepository<QuestStateEntity, Long> {
    List<QuestStateEntity> findByPlayer_Id(Long id);
}
