package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소재지지번주소
 * 학교의 소재지지번주소
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inmadr {
    @Column(name = "lnmadr")
    private String value;

    private Inmadr(final String value) {
        this.value = value;
    }

    public static Inmadr from(final String value) {
        return new Inmadr(value);
    }
}
