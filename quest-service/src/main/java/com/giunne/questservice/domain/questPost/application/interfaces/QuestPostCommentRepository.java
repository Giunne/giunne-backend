package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.questservice.domain.questPost.domain.QuestPostComment;

import java.util.List;

public interface QuestPostCommentRepository {

    QuestPostComment findById(Long id);

    QuestPostComment save(QuestPostComment comment);

    void delete(QuestPostComment comment);

    List<QuestPostComment> findByPlayerId(Long playerId);

    List<QuestPostComment> findByQuestPost(Long playerId);
}
