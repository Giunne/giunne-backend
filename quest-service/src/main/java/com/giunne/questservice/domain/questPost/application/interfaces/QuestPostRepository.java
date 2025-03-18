package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.questservice.domain.questPost.application.dto.response.GetPostDetailResponseDto;
import com.giunne.questservice.domain.questPost.application.dto.response.GetPostResponseDto;
import com.giunne.questservice.domain.questPost.domain.QuestPost;

import java.util.List;

public interface QuestPostRepository {
    QuestPost save(QuestPost questPost);

    GetPostResponseDto findById(Long id);

    List<GetPostDetailResponseDto> findMyQuest(Long questId, Long playerId);
}
