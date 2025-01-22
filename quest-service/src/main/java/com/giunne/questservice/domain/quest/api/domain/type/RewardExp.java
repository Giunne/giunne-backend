package com.giunne.questservice.domain.quest.api.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 보상 경험치
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RewardExp {
    @Column(name = "reward_exp", nullable = false)
    private Long value;

    private RewardExp(final Long value) {
        this.value = value;
    }

    public static RewardExp from(final Long value) {
        return new RewardExp(value);
    }
    
}
