package com.giunne.questservice.domain.questState.domain;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.quest.domain.Quest;
import com.giunne.questservice.domain.questState.domain.type.QuestProgress;
import com.giunne.questservice.domain.questState.domain.type.RewardExp;
import com.giunne.questservice.domain.questState.domain.type.RewardPoint;
import com.giunne.questservice.domain.questState.domain.type.StarPoint;
import com.giunne.questservice.domain.team.domain.Team;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class QuestState {
    private Long id;
    private Player player;
    private Quest quest;
    @Builder.Default
    private QuestProgress questProgress = QuestProgress.LOCK;
    private Team team;
    @Builder.Default
    private RewardExp rewardExp = RewardExp.from(0L);
    @Builder.Default
    private RewardPoint rewardPoint = RewardPoint.from(0L); // 대표유무
    @Builder.Default
    private StarPoint starPoint = StarPoint.from(0L);
}
