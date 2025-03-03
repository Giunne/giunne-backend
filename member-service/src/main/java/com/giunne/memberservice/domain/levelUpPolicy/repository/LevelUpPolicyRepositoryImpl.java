package com.giunne.memberservice.domain.levelUpPolicy.repository;

import com.giunne.memberservice.domain.levelUpPolicy.application.interfaces.LevelUpPolicyRepository;
import com.giunne.memberservice.domain.levelUpPolicy.domain.LevelUpPolicy;
import com.giunne.memberservice.domain.levelUpPolicy.repository.entity.LevelUpPolicyEntity;
import com.giunne.memberservice.domain.levelUpPolicy.repository.jpa.JpaLevelUpPolicyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class LevelUpPolicyRepositoryImpl implements LevelUpPolicyRepository {

    private final JpaLevelUpPolicyRepository jpaLevelUpPolicyRepository;

    @Override
    public LevelUpPolicy findByCurrentLevel(Long currentLevel) {
        Optional<LevelUpPolicyEntity> levelValue = jpaLevelUpPolicyRepository.findByCurrentLevel_Value(currentLevel);
        if (levelValue.isEmpty()) {
            return null;
        }
        LevelUpPolicyEntity levelUpPolicyEntity = levelValue.get();
        return levelUpPolicyEntity.toLevelUpPolicy();
    }
}
