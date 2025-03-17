package com.giunne.questservice.domain.questState.repository.jpa;

import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import com.giunne.questservice.domain.roadMap.repository.entity.RoadMapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaQuestStateRepository extends JpaRepository<QuestStateEntity, Long> {
    List<QuestStateEntity> findByPlayer_Id(Long id);

    Optional<QuestStateEntity> findByPlayer_AvatarIdAndQuest_Id(Long playerId, Long questId);
}
