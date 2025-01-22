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
public class MaxUser {
    @Column(name = "max_user", nullable = false)
    private Integer value;

    private MaxUser(final Integer value) {
        this.value = value;
    }

    public static MaxUser from(final Integer value) {
        return new MaxUser(value);
    }
}
