package com.giunne.questservice.domain.player.repository.entity;

import com.giunne.questservice.domain.player.domain.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_no")
    private Long id;

    public PlayerEntity(Player player) {
        this.id = player.getId();
    }

    public Player toPlayer() {
        return Player.builder()
                .id(id)
                .build();
    }

}
