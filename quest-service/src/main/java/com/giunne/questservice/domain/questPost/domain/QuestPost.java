package com.giunne.questservice.domain.questPost.domain;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostContent;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostTitle;
import com.giunne.questservice.domain.questState.domain.QuestState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestPost {
    private Long id;
    private Player player;
    @Builder.Default
    private QuestPostTitle questPostTitle = QuestPostTitle.from("");
    @Builder.Default
    private QuestPostContent questPostContent = QuestPostContent.from("");
    private QuestState questState;
    @Builder.Default
    private QuestPostProgressType questPostProgressType = QuestPostProgressType.UPLOAD;
}
