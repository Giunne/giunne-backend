package com.giunne.questservice.domain.quest.application;

import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.dto.request.*;
import com.giunne.questservice.domain.quest.application.dto.response.*;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.questPost.application.dto.request.GetPostRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostListResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
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
    private final QuestPostRepository questPostRepository;

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

    public PaginationModel<GetQuestSearchResponseDto> getQuestTypeList(GetQuestTypeSearchRequestDto dto){
        return questRepository.getQuestTypeList(dto);
    }

    public PaginationModel<GetUploadQuestResponseDto> findUploadQuest(GetUploadQuestForStudentRequestDto dto) {

        PaginationModel<GetUploadQuestResponseDto> postResponseDto = questPostRepository.findUploadQuest(dto);
        List<Long> avatardlist = postResponseDto.getData().stream().map(GetUploadQuestResponseDto::getAvatarId).toList();

        Response<List<GetMyRecreationAvatarResponseDto>> avatarProfileListInfo = memberInfoClient.getAvatarProfileListInfo(avatardlist);

        // playerId를 키로, playerInfo를 값으로 저장하는 Map 생성 (수정된 부분)
        Map<Long, GetMyRecreationAvatarResponseDto> playerInfoMap = avatarProfileListInfo.value().stream()
                .collect(Collectors.toMap(GetMyRecreationAvatarResponseDto::getId, Function.identity()));

        // 게시물 리스트에 플레이어 정보를 매핑
        List<GetUploadQuestResponseDto> result = postResponseDto.getData().stream()
                .peek(dtoItem -> dtoItem.setPlayerInfo(playerInfoMap.get(dtoItem.getAvatarId())))
                .toList();

        postResponseDto.setData(result);
        return postResponseDto;
    }

}
