package com.giunne.questservice.domain.questState.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.quest.repository.entity.QuestEntity;
import com.giunne.questservice.domain.questAttachment.domain.type.IsRepresent;
import com.giunne.questservice.domain.questState.domain.QuestState;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.domain.type.RewardExp;
import com.giunne.questservice.domain.questState.domain.type.RewardPoint;
import com.giunne.questservice.domain.questState.domain.type.StarPoint;
import com.giunne.questservice.domain.team.repository.entity.TeamEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "quest_state")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestStateEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quest_state_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quest_no")
    private QuestEntity quest; // 퀘스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no", nullable = true)
    private PlayerEntity player;

    @Enumerated(EnumType.STRING)
    @Column(name = "quest_progress", nullable = false)
    private QuestProgress questProgress = QuestProgress.LOCK;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_no")
    private TeamEntity team;

    @Embedded
    private RewardExp rewardExp = RewardExp.from(0L); // 대표유무

    @Embedded
    private RewardPoint rewardPoint = RewardPoint.from(0L); // 대표유무

    @Embedded
    private StarPoint starPoint = StarPoint.from(0L);

    @Embedded
    private Active isActive = Active.from(true);

    public QuestStateEntity( QuestState state ) {
        this.id = state.getId();
        this.quest = new QuestEntity(state.getQuest());
        this.player = new PlayerEntity(state.getPlayer());
        this.questProgress = state.getQuestProgress();
        this.team = new TeamEntity(state.getTeam());
        this.rewardExp = state.getRewardExp();
        this.rewardPoint = state.getRewardPoint();
        this.starPoint = state.getStarPoint();
    }

    public QuestState toState() {
        return QuestState.builder()
                .id(id)
                .quest(quest.toQuest())
                .player(player.toPlayer())
                .questProgress(questProgress)
                .team(team.toTeam())
                .rewardExp(rewardExp)
                .rewardPoint(rewardPoint)
                .starPoint(starPoint)
                .build();
    }

}
