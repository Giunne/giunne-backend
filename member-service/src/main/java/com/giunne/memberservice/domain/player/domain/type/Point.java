package com.giunne.memberservice.domain.player.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 보유 포인트
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {
    @Column(name = "point", nullable = false)
    private Long point;

    private Point(final Long value) {
        this.point = value;
    }

    public static Point from(final Long value) {
        return new Point(value);
    }
}
