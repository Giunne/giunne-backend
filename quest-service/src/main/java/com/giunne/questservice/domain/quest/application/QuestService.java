package com.giunne.questservice.domain.quest.application;

import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileListRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.dto.request.CreateQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.UpdateQuestInfoRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.quest.application.dto.response.UploadQuestInfoResponseDto;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final MemberInfoClient memberInfoClient;

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

    public List<UploadQuestInfoResponseDto> findUploadQuests(GetUploadQuestRequestDto dto){


        List<UploadQuestInfoResponseDto> uploadQuests = questRepository.findUploadQuests(dto.roadmapId());
        List<Long> playerIdlist = uploadQuests.stream().map(i -> i.getQuestStateInfo().getPlayerId()).toList();
        uploadQuests.stream().map(i -> i.getQuestStateInfo().getPlayerId()).toList();


        Response<List<GetMyRecreationAvatarResponseDto>> avatarProfileListInfo = memberInfoClient.getAvatarProfileListInfo(playerIdlist);

        // playerId를 키로, playerInfo를 값으로 저장하는 Map 생성
        Map<Long, GetMyRecreationAvatarResponseDto> playerInfoMap = avatarProfileListInfo.value().stream()
                .collect(Collectors.toMap(GetMyRecreationAvatarResponseDto::getId, Function.identity()));

        // uploadQuests에 playerInfo 매핑
        for (UploadQuestInfoResponseDto quest : uploadQuests) {
            Long playerId = quest.getQuestStateInfo().getPlayerId();
            GetMyRecreationAvatarResponseDto playerInfo = playerInfoMap.get(playerId);

            if (playerInfo != null) {
                quest.setPlayerInfo(playerInfo);
            }
        }

        return uploadQuests;
    }

}
