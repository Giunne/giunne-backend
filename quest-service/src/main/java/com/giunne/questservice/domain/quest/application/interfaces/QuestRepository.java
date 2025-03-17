package com.giunne.questservice.domain.quest.application.interfaces;

import com.giunne.questservice.domain.course.domain.Course;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestInfoResponseDto;
import com.giunne.questservice.domain.quest.domain.QuestOpenCondition;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.domain.Quest;

import java.util.List;

public interface QuestRepository {
    Quest saveQuest(Quest quest);

    Quest findById(Long id);

    void deleteQuest(Quest quest);

    Quest updateQuestInfo(UpdateQuestInfoRequestDto dto);

    List<Quest> findByRoadMap(Long roadMapId);

    List<QuestOpenCondition> insertOpenConditions(Quest node, List<Quest> openConditions);

    List<UploadQuestInfoResponseDto> findUploadQuests(Long roadMapId);
}
