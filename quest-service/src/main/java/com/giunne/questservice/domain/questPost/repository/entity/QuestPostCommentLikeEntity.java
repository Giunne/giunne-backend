package com.giunne.questservice.domain.questPost.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.domain.like.type.LikeId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_post_like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostCommentLikeEntity extends BaseEntity {
    @EmbeddedId
    private LikeId id;

    @Embedded
    private Active isActive = Active.from(true);

    public QuestPostCommentLikeEntity(QuestPostComment comment, Player player) {
        this.id = new LikeId(comment.getId(), player.getId());
    }
}
