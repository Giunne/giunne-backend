package com.giunne.questservice.domain.quest.domain.type;


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

    public Long calculatedPoint(Long starPoint, Boolean hasExtraPoints) {
        final double EXTRA_POINTS = hasExtraPoints ? 0.2 : 0.0;
        final double STAR_2_MULTIPLIER = 1.2 +  EXTRA_POINTS;
        final double STAR_3_MULTIPLIER = 1.4 + EXTRA_POINTS;

        return switch (starPoint.intValue()) {
            case 2 -> (long) Math.ceil(this.value * STAR_2_MULTIPLIER);
            case 3 -> (long) Math.ceil(this.value * STAR_3_MULTIPLIER);
            default -> this.value;
        };
    }
}
