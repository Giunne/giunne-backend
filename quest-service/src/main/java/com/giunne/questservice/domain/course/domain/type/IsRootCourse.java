package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsRootCourse {
    @Column(name = "is_root_course", nullable = false)
    private boolean value = false;

    private IsRootCourse(final boolean value) {
        this.value = value;
    }

    public static IsRootCourse from(final boolean value) {
        return new IsRootCourse(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }
}
