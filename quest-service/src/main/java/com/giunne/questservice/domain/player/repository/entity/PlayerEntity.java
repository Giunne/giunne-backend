package com.giunne.questservice.domain.player.repository.entity;

import com.giunne.questservice.domain.player.domain.Player;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"avatar_no"})
        })
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_no")
    private Long id;

    @Column(name = "avatar_no", nullable = false)
    private Long avatarId;

    public PlayerEntity(Player player) {
        this.id = player.getId();
        this.avatarId = player.getAvatarId();
    }

    public Player toPlayer() {
        return Player.builder()
                .id(id)
                .avatarId(avatarId)
                .build();
    }

}
