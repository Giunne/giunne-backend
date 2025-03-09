package com.giunne.questservice.domain.questState.application.interfaces;

import com.giunne.questservice.domain.questState.domain.QuestState;

import java.util.List;

public interface QuestStateRepository {
    QuestState findById(Long id);
    List<QuestState> findByPlayer(Long playerId);
    QuestState save(QuestState questState);
    List<QuestState> saveAll(List<QuestState> questStates);
}
