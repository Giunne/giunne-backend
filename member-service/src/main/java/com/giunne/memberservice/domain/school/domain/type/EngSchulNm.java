package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 학교명
 * 학교의 명칭
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EngSchulNm {
    @Column(name = "eng_schul_nm", nullable = false)
    private String value;

    private EngSchulNm(final String value) {
        this.value = value;
    }

    public static EngSchulNm from(final String value) {
        return new EngSchulNm(value);
    }
}
