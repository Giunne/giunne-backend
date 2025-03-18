package com.giunne.questservice.domain.questPost.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.questPost.domain.QuestPost;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostContent;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostTitle;
import com.giunne.questservice.domain.questPost.domain.post.type.QuestPostProgressType;
import com.giunne.questservice.domain.questState.repository.entity.QuestStateEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestPostEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_post_no")
    private Long id; // 퀘스트 게시물

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no", nullable = true)
    private PlayerEntity player;

    @Embedded
    private QuestPostTitle questPostTitle;

    @Embedded
    private QuestPostContent questPostContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_state_no", nullable = true)
    private QuestStateEntity questState;

    @Enumerated(EnumType.STRING)
    @Column(name = "quest_post_progress_type", nullable = false)
    private QuestPostProgressType questPostProgressType = QuestPostProgressType.UPLOAD;

    @Embedded
    private Active isActive = Active.from(true);

    public QuestPostEntity(QuestPost questPost) {
        this.id = questPost.getId();
        this.player = new PlayerEntity(questPost.getPlayer());
        this.questPostTitle = questPost.getQuestPostTitle();
        this.questPostContent = questPost.getQuestPostContent();
        this.questState = new QuestStateEntity(questPost.getQuestState());
        this.questPostProgressType = questPost.getQuestPostProgressType();
    }

    public QuestPost toQuestPost() {
        return QuestPost.builder()
                .id(id)
                .player(player.toPlayer())
                .questPostTitle(questPostTitle)
                .questPostContent(questPostContent)
                .questState(questState.toQuestState())
                .questPostProgressType(questPostProgressType)
                .build();
    }

}
