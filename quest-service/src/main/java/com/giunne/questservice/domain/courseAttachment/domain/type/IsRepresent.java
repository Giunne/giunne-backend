package com.giunne.questservice.domain.courseAttachment.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsRepresent {
    @Column(name = "is_represent", nullable = false)
    private boolean value = false;

    private IsRepresent(final boolean value) {
        this.value = value;
    }

    public static IsRepresent from(final boolean value) {
        return new IsRepresent(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }
}
