package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팩스번호
 * 학교의 대표 팩스번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgFaxno {

    @Column(name = "org_faxno")
    private String value;

    private OrgFaxno(final String value) {
        this.value = value;
    }

    public static OrgFaxno from(final String value) {
        return new OrgFaxno(value);
    }
}
