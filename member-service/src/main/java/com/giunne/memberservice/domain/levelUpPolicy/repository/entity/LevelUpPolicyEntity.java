package com.giunne.memberservice.domain.levelUpPolicy.repository.entity;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.memberservice.domain.levelUpPolicy.domain.LevelUpPolicy;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.CurrentLevel;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.NeedExp;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.TargetLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "level_up_policy",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"current_level"})
        }
)
public class LevelUpPolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_up_policy_no")
    private Long id; // 레벨업 기준 정보 번호

    @Embedded
    private TargetLevel targetLevel; // 목표 레벨

    @Embedded
    private CurrentLevel currentLevel; // 현재 레벨

    @Embedded
    private NeedExp needExp; // 필요 경험치

    @Embedded
    private Active isActive = Active.from(true);


    public LevelUpPolicyEntity(LevelUpPolicy levelUpPolicy) {
        this.id = levelUpPolicy.getId();
        this.targetLevel = levelUpPolicy.getTargetLevel();
        this.currentLevel = levelUpPolicy.getCurrentLevel();
        this.needExp = levelUpPolicy.getNeedExp();
    }

    public LevelUpPolicy toLevelUpPolicy() {
        return LevelUpPolicy.builder()
                .id(id)
                .targetLevel(targetLevel)
                .currentLevel(currentLevel)
                .needExp(needExp)
                .build();
    }

}
