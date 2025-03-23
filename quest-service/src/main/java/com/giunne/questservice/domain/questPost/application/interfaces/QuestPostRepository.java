package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.questservice.domain.quest.application.dto.request.GetQuestTypeSearchRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestForStudentRequestDto;
import com.giunne.questservice.domain.quest.application.dto.request.GetUploadQuestRequestDto;
import com.giunne.questservice.domain.quest.application.dto.response.GetUploadQuestResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.domain.QuestPost;

import java.util.List;

public interface QuestPostRepository {
    QuestPost save(QuestPost questPost);

    QuestPost getQuestPost(Long id);

    GetPostResponseDto findById(Long id);

    QuestPost getPost(Long id);

    List<GetPostDetailResponseDto> findMyQuest(Long questId, Long playerId);

    PaginationModel<GetUploadQuestResponseDto> findUploadQuest(GetUploadQuestForStudentRequestDto dto);

    QuestPost updatePostProgress(QuestPost questPost);
}
