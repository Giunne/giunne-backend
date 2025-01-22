package com.giunne.questservice.domain.roadMap.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 설명
 */

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Description {
    @Column(name = "description")
    private String value;

    private Description(final String value) {
        this.value = value;
    }

    public static Description from(final String value) {
        return new Description(value);
    }

    private void setValue(String value) {this.value = value;}
}
