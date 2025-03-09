package com.giunne.questservice.domain.questState.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HasExtraPoints {

    @Column(name = "has_extra_points", nullable = false)
    @ColumnDefault("false")
    private boolean value = false;

    private HasExtraPoints(final boolean value) {
        this.value = value;
    }

    public static HasExtraPoints from(final boolean value) {
        return new HasExtraPoints(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }

}
