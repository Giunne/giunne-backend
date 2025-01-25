package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 소재지도로명주소
 * 학교의 소재지도로명주소
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Rdnmadr {
    @Column(name = "rdnmadr")
    private String value;

    private Rdnmadr(final String value) {
        this.value = value;
    }

    public static Rdnmadr from(final String value) {
        return new Rdnmadr(value);
    }
}
