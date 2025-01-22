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
public class SchoolNm {  // 학교명

    @Column(name = "school_nm", nullable = false)
    private String value;

    private SchoolNm(final String value) {
        this.value = value;
    }

    public static SchoolNm from(final String value) {
        return new SchoolNm(value);
    }
}
