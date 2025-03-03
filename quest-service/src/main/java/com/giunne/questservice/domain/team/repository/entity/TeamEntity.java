package com.giunne.questservice.domain.team.repository.entity;

import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.team.domain.Team;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "team")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeamEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_no")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_no", nullable = true)
    private PlayerEntity player;


    public TeamEntity(Team team) {
        this.id = team.getId();
        this.player = new PlayerEntity(team.getPlayer());
    }

    public Team toTeam() {
        return Team.builder()
                .id(id)
                .player(player.toPlayer())
                .build();
    }

}
