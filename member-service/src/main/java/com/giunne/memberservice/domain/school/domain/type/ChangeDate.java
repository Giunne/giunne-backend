package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 수정일자
 * 정보가 수정 및 저장된 일자
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChangeDate {
    @Column(name = "change_date", nullable = false)
    private LocalDateTime value;

    private ChangeDate(final LocalDateTime value) {
        this.value = value;
    }

    public static ChangeDate from(final LocalDateTime value) {
        return new ChangeDate(value);
    }
}
