package com.giunne.memberservice.domain.levelUpPolicy.application.interfaces;

import com.giunne.memberservice.domain.levelUpPolicy.domain.LevelUpPolicy;

public interface LevelUpPolicyRepository {
    LevelUpPolicy findByCurrentLevel(Long currentLevel);
}
