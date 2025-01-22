package com.giunne.itemservice.domain.item.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 가격
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Price {
    @Column(name = "price", nullable = false)
    private Long value;

    public Price(Long value) {
        this.value = value;
    }

    public static Price from(final Long value) {
        return new Price(value);
    }
}
