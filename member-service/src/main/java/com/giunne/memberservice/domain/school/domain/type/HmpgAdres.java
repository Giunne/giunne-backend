package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 홈페이지주소
 * 학교의 홈페이지 주소(URL)
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HmpgAdres {

    @Column(name = "hmpg_adres")
    private String value;

    private HmpgAdres(final String value) {
        this.value = value;
    }

    public static HmpgAdres from(final String value) {
        return new HmpgAdres(value);
    }
}
