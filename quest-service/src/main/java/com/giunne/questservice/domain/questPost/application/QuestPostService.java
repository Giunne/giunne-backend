package com.giunne.questservice.domain.questPost.application;

import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.Response;
import com.giunne.questservice.domain.quest.application.interfaces.QuestRepository;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.questPost.application.dto.request.GetPostRequestDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostListResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.QuestInfoResponseDto;
import com.giunne.questservice.domain.questPost.application.interfaces.QuestPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestPostService {

    private final QuestPostRepository questPostRepository;
    private final QuestRepository questRepository;
    private final MemberInfoClient memberInfoClient;

    public GetPostResponseDto findById(Long id) {
        GetPostResponseDto postResponseDto = questPostRepository.findById(id);
        Quest questInfo = questRepository.findById(postResponseDto.getQuestId());

        Response<GetMyRecreationAvatarResponseDto> avatarProfileInfo = memberInfoClient.getAvatarProfileInfo(postResponseDto.getPlayerId());
        postResponseDto.setPlayerInfo(avatarProfileInfo.value());
        postResponseDto.setQuestInfo(new QuestInfoResponseDto(questInfo));
        return postResponseDto;
    }

    public GetPostListResponseDto findMyQuest(GetPostRequestDto dto) {
        Response<GetMyRecreationAvatarResponseDto> avatarProfileInfo = memberInfoClient.getAvatarProfileInfo(dto.playerId());
        Quest questInfo = questRepository.findById(dto.questId());

        List<GetPostDetailResponseDto> postResponseDto = questPostRepository.findMyQuest(dto.questId(), dto.playerId());
        return GetPostListResponseDto.builder()
                .postInfoList(postResponseDto)
                .playerInfo(avatarProfileInfo.value())
                .questInfo(new QuestInfoResponseDto(questInfo))
                .build();
    }
}
