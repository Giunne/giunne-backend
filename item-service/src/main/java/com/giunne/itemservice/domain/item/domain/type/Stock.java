package com.giunne.itemservice.domain.item.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 재고
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stock {
    @Column(name = "stock", nullable = false)
    private Long value;

    private Stock(final Long value) {
        this.value = value;
    }

    public static Stock from(final Long value) {
        return new Stock(value);
    }
}
