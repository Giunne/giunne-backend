package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도로명우편번호
 * 학교의 도로명주소 우편번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrgRdnzc {
    @Column(name = "org_rdnzc")
    private String value;

    private OrgRdnzc(final String value) {
        this.value = value;
    }

    public static OrgRdnzc from(final String value) {
        return new OrgRdnzc(value);
    }
}
