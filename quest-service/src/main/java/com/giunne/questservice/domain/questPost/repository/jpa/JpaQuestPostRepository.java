package com.giunne.questservice.domain.questPost.repository.jpa;

import com.giunne.questservice.domain.questPost.repository.entity.QuestPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaQuestPostRepository extends JpaRepository<QuestPostEntity, Long> {
}
