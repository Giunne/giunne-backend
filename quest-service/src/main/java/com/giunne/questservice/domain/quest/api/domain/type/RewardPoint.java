package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 보상 포인트
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardPoint {
    @Column(name = "reward_point", nullable = false)
    private Long value;

    private RewardPoint(final Long value) {
        this.value = value;
    }

    public static RewardPoint from(final Long value) {
        return new RewardPoint(value);
    }
    
}
