package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 팀전 여부
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class isTeam {

    @Column(name = "is_team", nullable = false)
    private boolean value = false;

    private isTeam(final boolean value) {
        this.value = value;
    }

    public static isTeam from(final boolean value) {
        return new isTeam(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }

}
