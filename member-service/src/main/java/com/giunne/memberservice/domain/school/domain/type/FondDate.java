package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 설립일자
 * 학교의 설립인가를 받은 일자
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FondDate {
    @Column(name = "fond_date", nullable = false)
    private LocalDateTime value;

    private FondDate(final LocalDateTime value) {
        this.value = value;
    }

    public static FondDate from(final LocalDateTime value) {
        return new FondDate(value);
    }
}

