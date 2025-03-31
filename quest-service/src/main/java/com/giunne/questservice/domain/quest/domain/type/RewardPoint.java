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
        final double STAR_2_MULTIPLIER =  hasExtraPoints ? 0.2 : 0.0;
        final double STAR_3_MULTIPLIER = hasExtraPoints ? 0.4 : 0.0;

        return switch (starPoint.intValue()) {
            case 2 -> (long) Math.ceil(this.value * (1.0 + STAR_2_MULTIPLIER+ EXTRA_POINTS));
            case 3 -> (long) Math.ceil(this.value * (1.0 + STAR_3_MULTIPLIER+ EXTRA_POINTS));
            default -> this.value;
        };
    }
}
