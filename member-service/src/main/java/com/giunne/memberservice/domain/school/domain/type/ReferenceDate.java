package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 	데이터기준일자
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReferenceDate {

    @Column(name = "reference_date", nullable = false)
    private LocalDateTime value;

    private ReferenceDate(final LocalDateTime value) {
        this.value = value;
    }

    public static ReferenceDate from(final LocalDateTime value) {
        return new ReferenceDate(value);
    }
}
