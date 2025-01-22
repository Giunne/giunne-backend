package com.giunne.itemservice.domain.store.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상점명
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreName {
    @Column(name = "store_name", nullable = false)
    private String value;

    public StoreName(String value) {
        this.value = value;
    }

    public static StoreName from(final String value) {
        return new StoreName(value);
    }
}
