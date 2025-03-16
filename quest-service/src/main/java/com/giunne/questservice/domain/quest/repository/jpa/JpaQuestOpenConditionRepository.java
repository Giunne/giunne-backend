package com.giunne.questservice.domain.quest.repository.jpa;

import com.giunne.questservice.domain.quest.repository.entity.QuestOpenConditionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestOpenConditionRepository extends JpaRepository<QuestOpenConditionEntity, Long> {
}
