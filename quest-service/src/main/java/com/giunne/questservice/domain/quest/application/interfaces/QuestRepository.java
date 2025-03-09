package com.giunne.questservice.domain.quest.application.interfaces;

import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.domain.Quest;

import java.util.List;

public interface QuestRepository {
    Quest saveQuest(Quest quest);
    Quest findById(Long id);
    void deleteQuest(Quest quest);
    Quest updateQuestInfo(UpdateQuestInfoRequestDto dto);
    List<Quest> findByRoadMap(Long roadMapId);
}
