package com.giunne.questservice.domain.questPost.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.questPost.domain.QuestPostComment;
import com.giunne.questservice.domain.questPost.domain.comment.type.QuestPostCommentContent;
import com.giunne.questservice.domain.questPost.domain.comment.type.QuestPostCommentLikeCounter;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_post_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostCommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_post_comment_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no", nullable = true)
    private PlayerEntity player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_post_no", nullable = true)
    private QuestPostEntity post;

    @Embedded
    private QuestPostCommentContent content;

    @Embedded
    private QuestPostCommentLikeCounter likeCounter;

    @Embedded
    private Active isActive = Active.from(true);

    public QuestPostCommentEntity(QuestPostComment comment) {
        this.id = comment.getId();
        this.player = new PlayerEntity(comment.getPlayer());
        this.post = new QuestPostEntity(comment.getPost());
        this.content = comment.getContent();
        this.likeCounter = comment.getLikeCounter();
    }

    public QuestPostComment toQuestPostComment() {
        return QuestPostComment.builder()
                .id(this.id)
                .player(this.player.toPlayer())
                .post(this.post.toQuestPost())
                .content(this.content)
                .likeCounter(this.likeCounter)
                .build();
    }

}
