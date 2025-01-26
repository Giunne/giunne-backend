package com.giunne.memberservice.domain.player.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 레벨
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Level {

    @Column(name = "level", nullable = false)
    private Long level;

    private Level(final Long value) {
        this.level = value;
    }

    public static Level from(final Long value) {
        return new Level(value);
    }

}
