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
    private QuestProgress questProgress;
    private Team team;
    private RewardExp rewardExp; // 대표유무
    private RewardPoint rewardPoint; // 대표유무
    private StarPoint starPoint;
}
