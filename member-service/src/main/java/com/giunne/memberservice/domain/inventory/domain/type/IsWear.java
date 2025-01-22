package com.giunne.memberservice.domain.inventory.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 착용 유무
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsWear {
    @Column(name = "is_wear")
    private Boolean  value;

    private IsWear(final Boolean  value) {
        this.value = value;
    }

    public static IsWear from(final Boolean  value) {
        return new IsWear(value);
    }
}
