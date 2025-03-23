package com.giunne.memberservice.domain.levelUpPolicy.domain;

import com.giunne.memberservice.domain.levelUpPolicy.domain.type.CurrentLevel;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.NeedExp;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.TargetLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class LevelUpPolicy {
    private Long id; // 레벨업 기준 정보 번호
    private TargetLevel targetLevel; // 목표 레벨
    private CurrentLevel currentLevel; // 현재 레벨
    private NeedExp needExp; // 필요 경험치


    public boolean isMaxLevel() {
        return Objects.equals(currentLevel.getValue(), targetLevel.getValue());
    }

}
