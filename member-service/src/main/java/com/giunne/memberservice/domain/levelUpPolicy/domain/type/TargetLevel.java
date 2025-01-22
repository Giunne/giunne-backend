package com.giunne.memberservice.domain.levelUpPolicy.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 목표 레벨
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TargetLevel {

    @Column(name = "target_level", nullable = false)
    private Long value;

    private TargetLevel(final Long value) {
        this.value = value;
    }

    public static TargetLevel from(final Long value) {
        return new TargetLevel(value);
    }

}
