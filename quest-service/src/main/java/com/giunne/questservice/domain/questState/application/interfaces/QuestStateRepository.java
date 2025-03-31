package com.giunne.questservice.domain.questState.application.interfaces;

import com.giunne.questservice.domain.questState.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questState.domain.QuestState;

import java.util.List;

public interface QuestStateRepository {
    QuestState findById(Long id);

    List<QuestState> findByPlayer(Long playerId);

    QuestState save(QuestState questState);

    QuestState findByPlayerAndQuest(Long playerId, Long questId);

    List<QuestState> saveAll(List<QuestState> questStates);

    void updateChildQuestOpen(QuestState questState);

    boolean existByCheckQuest(QuestState questState);

    List<QuestInfoResponseDto> findInProgressQuestByRoadMap(Long roadMapId, Long avatarId);

    List<QuestInfoResponseDto> findConfirmQuestByRoadMap(Long roadMapId, Long avatarId);

    QuestState findByQuestPostId(Long questPostId);
}
