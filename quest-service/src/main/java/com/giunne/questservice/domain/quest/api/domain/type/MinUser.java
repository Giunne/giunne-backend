package com.giunne.questservice.domain.quest.api.domain.type;

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
public class MinUser {
    @Column(name = "min_user", nullable = false)
    private Integer value;

    private MinUser(final Integer value) {
        this.value = value;
    }

    public static MinUser from(final Integer value) {
        return new MinUser(value);
    }
}
