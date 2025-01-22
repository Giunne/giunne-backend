package com.giunne.itemservice.domain.cart.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 수량
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private Long value;

    private Quantity(final Long value) {
        this.value = value;
    }

    public static Quantity from(final Long value) {
        return new Quantity(value);
    }

}
