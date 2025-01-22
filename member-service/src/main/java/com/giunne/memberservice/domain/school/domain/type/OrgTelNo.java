package com.giunne.memberservice.domain.school.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 전화번호	
 * 학교의 대표 전화번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgTelNo {
    @Column(name = "org-tel_no")
    private String value;

    private OrgTelNo(final String value) {
        this.value = value;
    }

    public static OrgTelNo from(final String value) {
        return new OrgTelNo(value);
    }
}
