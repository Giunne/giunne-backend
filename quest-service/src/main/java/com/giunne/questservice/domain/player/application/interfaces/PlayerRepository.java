package com.giunne.questservice.domain.player.application.interfaces;

import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;

import java.util.Optional;

public interface PlayerRepository {
    Optional<PlayerEntity> findById(Long id);
    Optional<PlayerEntity> findByAvatarId(Long id);
    Player save(PlayerEntity playerEntity);
    boolean existsByAvatarId(Long id);
}
