package com.giunne.memberservice.domain.avatar.domain.type;

import jakarta.persistence.Column;

/**
 * 기수번호
 */

public class BaseNumber {

    @Column(name = "base_number")
    private Long baseNumber;

    private BaseNumber(final Long baseNumber) {
        this.baseNumber = baseNumber;
    }

    public static BaseNumber from(final Long value) {
        return new BaseNumber(value);
    }
}
