package com.giunne.memberservice.domain.player.domain.type;

import jakarta.persistence.Column;

/**
 * 기수번호
 */

public class BaseNumber {

    @Column(name = "base_number")
    private String baseNumber;

    private BaseNumber(final String baseNumber) {
        this.baseNumber = baseNumber;
    }

    public static BaseNumber from(final String value) {
        return new BaseNumber(value);
    }
}
