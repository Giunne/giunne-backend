package com.giunne.questservice.domain.questPost.domain;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.questPost.domain.comment.type.QuestPostCommentContent;
import com.giunne.questservice.domain.questPost.domain.comment.type.QuestPostCommentLikeCounter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestPostComment {
    private Long id;
    private Player player;
    private QuestPost post;
    @Builder.Default
    private QuestPostCommentContent content = QuestPostCommentContent.from("");
    @Builder.Default
    private QuestPostCommentLikeCounter likeCounter = QuestPostCommentLikeCounter.from(0L);
}
