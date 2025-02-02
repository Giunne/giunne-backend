package com.giunne.memberservice.domain.recreation.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 기수번호
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
