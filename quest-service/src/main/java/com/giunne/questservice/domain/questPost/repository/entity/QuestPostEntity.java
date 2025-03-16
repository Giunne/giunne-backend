package com.giunne.questservice.domain.questPost.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import com.giunne.questservice.domain.questPost.domain.type.QuestPostContent;
import com.giunne.questservice.domain.questPost.domain.type.QuestPostTitle;
import com.giunne.questservice.domain.questPost.domain.type.QuestPostProgressType;
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
    @JoinColumn(name = "quest_no")
    private QuestEntity quest; // 퀘스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_state_no", nullable = true)
    private QuestStateEntity questStateEntity;

    @Enumerated(EnumType.STRING)
    @Column(name = "quest_post_progress_type", nullable = false)
    private QuestPostProgressType questPostProgressType = QuestPostProgressType.UPLOAD;

}
