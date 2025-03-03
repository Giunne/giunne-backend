package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {

    @Column(name = "position_x", nullable = false)
    private Double positionX;

    @Column(name = "position_y", nullable = false)
    private Double positionY;


    @Builder
    private Position(final Double positionX, final Double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public static Position of(final Double positionX, final Double positionY) {
        return new Position(positionX, positionY);
    }

}
