package com.giunne.memberservice.domain.levelUpPolicy.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 필요 경험치
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NeedExp {

    @Column(name = "need_exp", nullable = false)
    private Long value;

    private NeedExp(final Long value) {
        this.value = value;
    }

    public static NeedExp from(final Long value) {
        return new NeedExp(value);
    }

}
