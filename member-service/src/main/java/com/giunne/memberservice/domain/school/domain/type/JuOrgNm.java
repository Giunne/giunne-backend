package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 관할조직명
 * 학교가 소속되어 있는 있는 지역의 관할 시도교육청
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JuOrgNm {

    @Column(name = "ju_org_nm", nullable = false)
    private String value;

    private JuOrgNm(final String value) {
        this.value = value;
    }

    public static JuOrgNm from(final String value) {
        return new JuOrgNm(value);
    }

}
