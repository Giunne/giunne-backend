package com.giunne.memberservice.domain.inventory.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 보유 유무
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HasItem {
    @Column(name = "has_item")
    private Boolean  value;

    private HasItem(final Boolean  value) {
        this.value = value;
    }

    public static HasItem from(final Boolean  value) {
        return new HasItem(value);
    }
}
