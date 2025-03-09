package com.giunne.questservice.domain.quest.application;

import com.giunne.questservice.domain.quest.application.dto.request.CreateQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;

    public Quest insertQuest(CreateQuestRequestDto dto) {
        Quest quest = dto.toQuest();
        return questRepository.saveQuest(quest);
    }

    public Quest findById(Long id) {
        return questRepository.findById(id);
    }

    public void deleteQuest(Quest quest){
        questRepository.deleteQuest(quest);
    }

    public QuestInfoResponseDto updateQuestInfo(UpdateQuestInfoRequestDto dto){
        Quest quest = questRepository.updateQuestInfo(dto);
        return new QuestInfoResponseDto(quest);
    }

}
