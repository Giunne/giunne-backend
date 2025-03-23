package com.giunne.memberservice.domain.levelUpPolicy.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 현재 레벨
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrentLevel {

    @Column(name = "current_level", nullable = false)
    private Long value;

    private CurrentLevel(final Long value) {
        this.value = value;
    }

    public static CurrentLevel from(final Long value) {
        return new CurrentLevel(value);
    }

}
