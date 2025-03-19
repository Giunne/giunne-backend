package com.giunne.questservice.domain.questPost.domain;

import com.giunne.questservice.domain.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestPostCommentLike {
    private Long id;
    private Player player;
    private QuestPostComment comment;

}
