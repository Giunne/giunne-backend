package com.giunne.memberservice.domain.member.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 학생의 반 번호
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudentNumber {
    @Column(name = "student_number")
    private String value;

    private StudentNumber(final String value) {
        this.value = value;
    }

    public static StudentNumber from(final String value) {
        return new StudentNumber(value);
    }
}
