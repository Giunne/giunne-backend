package com.giunne.itemservice.domain.cart.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 총 가격
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TotalPrice {

    @Column(name = "total_price", nullable = false)
    private Long value;

    private TotalPrice(final Long value) {
        this.value = value;
    }

    public static TotalPrice from(final Long value) {
        return new TotalPrice(value);
    }

}
