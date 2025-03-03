package com.giunne.questservice.domain.questState.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 보상 포인트
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StarPoint {
    @Column(name = "star_point", nullable = false)
    private Long value;

    private StarPoint(final Long value) {
        this.value = value;
    }

    public static StarPoint from(final Long value) {
        return new StarPoint(value);
    }
    
}
