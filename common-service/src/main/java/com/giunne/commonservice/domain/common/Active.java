package com.giunne.commonservice.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Active {

    @Column(name = "is_active", nullable = false)
    private boolean value = true;

    private Active(final boolean value) {
        this.value = value;
    }

    public static Active from(final boolean value) {
        return new Active(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }

}
