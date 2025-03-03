package com.giunne.questservice.domain.team.domain;

import com.giunne.questservice.domain.player.domain.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Team {
    private Long id;
    private Player player;
}
