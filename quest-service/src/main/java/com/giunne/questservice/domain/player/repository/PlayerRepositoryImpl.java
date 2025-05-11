package com.giunne.questservice.domain.player.repository;

import com.giunne.questservice.domain.player.application.interfaces.PlayerRepository;
import com.giunne.questservice.domain.player.domain.Player;
import com.giunne.questservice.domain.player.repository.entity.PlayerEntity;
import com.giunne.questservice.domain.player.repository.jpa.JpaPlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PlayerRepositoryImpl implements PlayerRepository {
    private final JpaPlayerRepository jpaPlayerRepository;

    public Optional<PlayerEntity> findById(Long id) {
        return jpaPlayerRepository.findById(id);
    }

    public Optional<PlayerEntity> findByAvatarId(Long id) {
        return jpaPlayerRepository.findByAvatarId(id);
    }

    public boolean existsByAvatarId(Long id) {
        return jpaPlayerRepository.existsByAvatarId(id);
    }

    @Transactional
    public Player save(PlayerEntity playerEntity) {
        return jpaPlayerRepository.save(playerEntity).toPlayer();
    }

    @Transactional
    public void deleteByAvatarId(Long avatarId) {
        jpaPlayerRepository.deleteByAvatarId(avatarId);
    }

}
