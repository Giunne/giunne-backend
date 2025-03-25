package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.questservice.domain.questPost.domain.QuestPostComment;

public interface QuestPostCommentRepository {

    QuestPostComment findById(Long id);

    QuestPostComment save(QuestPostComment comment);

    void delete(QuestPostComment comment);
}
