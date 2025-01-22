package com.giunne.itemservice.domain.item.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 착용가능 레벨
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NeedLevel {

    @Column(name = "need_level", nullable = false)
    private Long value;

    private NeedLevel(final Long value) {
        this.value = value;
    }

    public static NeedLevel from(final Long value) {
        return new NeedLevel(value);
    }

}