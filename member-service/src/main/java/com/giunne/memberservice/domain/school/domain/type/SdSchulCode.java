package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 행정표준코드
 * 각급 기관의 행정업무에 필요한 행정코드를 표준화하여 정해진 절차에 따라 제정·고시한 행정코드
 * ※ 행정안전부 행정표준코드 관리시스템 제공(code.go.kr)
 */


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SdSchulCode {

    @Column(name = "sd_schul_code")
    private String value;

    public SdSchulCode(final String value) {
        this.value = value;
    }

    public static SdSchulCode from(final String value) {
        return new SdSchulCode(value);
    }

    private void setValue(String value) {this.value = value;}
}
