package com.giunne.questservice.domain.questPost.application.interfaces;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;

public interface QuestPostCommentLikeRepository {

    boolean checkLike(QuestPostComment comment, Player player);

    void like(QuestPostComment comment, Player player);

    void unlike(QuestPostComment comment, Player player);

    void deleteByComment(QuestPostComment comment);
}
