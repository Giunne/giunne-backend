package com.giunne.memberservice.domain.recreation.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 레크레이션명
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecreationName {

    @Column(name = "recreation_name", nullable = false)
    private String value;

    private RecreationName(final String value) {
        this.value = value;
    }

    public static RecreationName from(final String value) {
        return new RecreationName(value);
    }
}
