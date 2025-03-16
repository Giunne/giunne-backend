package com.giunne.questservice.domain.questState.application;


import com.giunne.questservice.domain.questState.application.dto.request.UpdateQuestStateRequestDto;
import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.commonservice.infra.external.domain.quest.client.dto.request.CreateQuestStateRequestDto;
import com.giunne.questservice.domain.questState.application.interfaces.QuestStateRepository;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestStateService {
    private final QuestStateRepository questStateRepository;
    private final QuestRepository questRepository;
    private final PlayerRepository playerRepository;

    public void savePlayerQuestStates(CreateQuestStateRequestDto dto) {
        List<Quest> quests = questRepository.findByRoadMap(dto.roadMapId());

        Player playerEntity;
        Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findByAvatarId(dto.avatarId());
        if (optionalPlayerEntity.isEmpty()) {
            playerEntity = playerRepository.save(new PlayerEntity(
                    Player.builder()
                            .avatarId(dto.avatarId())
                            .build()
            ));
        } else {
            playerEntity = optionalPlayerEntity.get().toPlayer();
        }

        List<QuestState> questStates = quests.stream().map(i -> QuestState.builder()
                        .quest(i)
                        .player(playerEntity)
                        .questProgress(i.getStartQuestProgress())
                        .build())
                .toList();
        questStateRepository.saveAll(questStates);
    }


    @Transactional
    public void updateQuestProgress(UpdateQuestStateRequestDto dto) {
        QuestState questState = questStateRepository.findById(dto.questStateId());

        QuestProgress questProgress = QuestProgress.from(dto.questProgress());
        questState.updateQuestProgress(questProgress);
        QuestState save = questStateRepository.save(questState);

        questStateRepository.updateChildQuestOpen(save);

    }

}
