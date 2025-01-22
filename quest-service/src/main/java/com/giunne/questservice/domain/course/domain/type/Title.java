package com.giunne.questservice.domain.course.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 제목
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {
    @Column(name = "title")
    private String value;

    private Title(final String value) {
        this.value = value;
    }

    public static Title from(final String value) {
        return new Title(value);
    }

    private void setValue(String value) {this.value = value;}
}
