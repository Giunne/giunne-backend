package com.giunne.memberservice.domain.player.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 경험치
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exp {

    @Column(name = "exp", nullable = false)
    private Long value;

    private Exp(final Long value) {
        this.value = value;
    }

    public static Exp from(final Long value) {
        return new Exp(value);
    }

}
