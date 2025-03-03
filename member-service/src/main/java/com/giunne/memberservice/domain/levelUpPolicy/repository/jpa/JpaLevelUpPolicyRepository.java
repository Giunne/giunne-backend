package com.giunne.memberservice.domain.levelUpPolicy.repository.jpa;

import com.giunne.memberservice.domain.levelUpPolicy.repository.entity.LevelUpPolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaLevelUpPolicyRepository extends JpaRepository<LevelUpPolicyEntity, Long> {
    Optional<LevelUpPolicyEntity> findByCurrentLevel_Value(Long currentLevel);
}
