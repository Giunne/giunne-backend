package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 난이도
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DifficultyLevel {

    @Column(name = "difficulty_level", nullable = false)
    private Long value;

    private DifficultyLevel(final Long value) {
        this.value = value;
    }

    public static DifficultyLevel from(final Long value) {
        return new DifficultyLevel(value);
    }

}
