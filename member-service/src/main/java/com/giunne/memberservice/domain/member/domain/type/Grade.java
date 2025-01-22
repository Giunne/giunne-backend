package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 학년
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Grade {
    @Column(name = "grade")
    private String value;

    private Grade(final String value) {
        this.value = value;
    }

    public static Grade from(final String value) {
        return new Grade(value);
    }
}
