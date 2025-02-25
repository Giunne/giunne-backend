package com.giunne.itemservice.domain.itemImage.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 착용가능 레벨
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level {

    @Column(name = "level", nullable = false)
    private Long value;

    private Level(final Long value) {
        this.value = value;
    }

    public static Level from(final Long value) {
        return new Level(value);
    }

}