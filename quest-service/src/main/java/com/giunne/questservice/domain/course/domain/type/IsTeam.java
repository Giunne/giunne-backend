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
public class IsTeam {

    @Column(name = "is_team")
    private boolean value = false;

    private IsTeam(final boolean value) {
        this.value = value;
    }

    public static IsTeam from(final boolean value) {
        return new IsTeam(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }

}
