package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 최대 인원수
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinPlayer {
    @Column(name = "min_user", nullable = false)
    private Integer value;

    private MinPlayer(final Integer value) {
        this.value = value;
    }

    public static MinPlayer from(final Integer value) {
        return new MinPlayer(value);
    }
}
