package com.giunne.questservice.domain.quest.api.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 클리어 여부
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsClear {

    @Column(name = "is_clear", nullable = false)
    private boolean value = false;

    private IsClear(final boolean value) {
        this.value = value;
    }

    public static IsClear from(final boolean value) {
        return new IsClear(value);
    }

    public boolean changeValue() {
        this.value = !this.value;
        return this.value;
    }

}
