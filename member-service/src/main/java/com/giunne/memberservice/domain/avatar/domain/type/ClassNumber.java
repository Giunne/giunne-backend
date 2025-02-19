package com.giunne.memberservice.domain.avatar.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 반
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClassNumber {
    @Column(name = "class_number")
    private String value;

    private ClassNumber(final String value) {
        this.value = value;
    }

    public static ClassNumber from(final String value) {
        return new ClassNumber(value);
    }
}
