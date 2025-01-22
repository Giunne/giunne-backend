package com.giunne.memberservice.domain.school.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 생성일자
 * 정보가 생성 및 저장된 일자
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatDate {

    @Column(name = "creat_date", nullable = false)
    private LocalDateTime value;

    private CreatDate(final LocalDateTime value) {
        this.value = value;
    }

    public static CreatDate from(final LocalDateTime value) {
        return new CreatDate(value);
    }
}
