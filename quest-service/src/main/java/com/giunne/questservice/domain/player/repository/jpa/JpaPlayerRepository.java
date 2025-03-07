package com.giunne.questservice.domain.player.repository.jpa;

import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPlayerRepository extends JpaRepository<PlayerEntity, Long> {
    Optional<PlayerEntity> findByAvatarId(Long avatarId);

}
