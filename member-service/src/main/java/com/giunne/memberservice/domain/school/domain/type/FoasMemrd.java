package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 개교기념일
 * 학교의 설립 이후 학생들이 처음으로 입학한 일자
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FoasMemrd {
    @Column(name = "foas_memrd", nullable = false)
    private LocalDateTime value;

    private FoasMemrd(final LocalDateTime value) {
        this.value = value;
    }

    public static FoasMemrd from(final LocalDateTime value) {
        return new FoasMemrd(value);
    }
}

