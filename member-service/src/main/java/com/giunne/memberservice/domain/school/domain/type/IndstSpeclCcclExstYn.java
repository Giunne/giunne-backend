package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 산업체특별학급존재여부
 * 산업체에 근무하는 청소년들에게 중등학교 과정의 교육기회를 제공하는 학급 또는 학교의 구분
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IndstSpeclCcclExstYn {
    @Column(name = "indst_specl_cccl_exst_yn", nullable = false)
    private String value;

    private IndstSpeclCcclExstYn(final String value) {
        this.value = value;
    }

    public static IndstSpeclCcclExstYn from(final String value) {
        return new IndstSpeclCcclExstYn(value);
    }
}
