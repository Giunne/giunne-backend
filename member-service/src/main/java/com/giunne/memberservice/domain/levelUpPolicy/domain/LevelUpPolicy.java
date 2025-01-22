package com.giunne.memberservice.domain.levelUpPolicy.domain;

import com.giunne.commonservice.domain.common.Active;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.NeedExp;
import com.giunne.memberservice.domain.levelUpPolicy.domain.type.TargetLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "level_up_policy")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LevelUpPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "level_up_policy_no")
    private Long id; // 레벨업 기준 정보 번호

    @Embedded
    private TargetLevel targetLevel; // 목표 레벨

    @Embedded
    private NeedExp needExp; // 필요 경험치

    @Embedded
    private Active isActive = Active.from(true);
}
