package com.giunne.questservice.domain.questState.repository;

import com.giunne.questservice.QuestTestConfiguration;
import com.giunne.questservice.domain.questState.application.QuestStateService;
import com.giunne.questservice.domain.questState.application.dto.request.UpdateQuestStateRequestDto;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ContextConfiguration(classes = QuestTestConfiguration.class)
@Transactional
class QuestStateRepositoryImplTest {

    @Autowired
    private QuestStateRepository questStateRepository;
    @Autowired
    private QuestStateService questStateService;

    @Test
    public void updateChildQuestOpen(){
        UpdateQuestStateRequestDto updateQuestStateRequestDto = UpdateQuestStateRequestDto.builder()
                .questStateId(1735L)
                .questProgress(QuestProgress.CONFIRM.name())
                .build();

        questStateService.updateQuestProgress(updateQuestStateRequestDto);


    }
}