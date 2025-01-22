package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 코스명
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CourseName {
    @Column(name = "course_name")
    private String value;

    private CourseName(final String value) {
        this.value = value;
    }

    public static CourseName from(final String value) {
        return new CourseName(value);
    }

    private void setValue(String value) {this.value = value;}
}
