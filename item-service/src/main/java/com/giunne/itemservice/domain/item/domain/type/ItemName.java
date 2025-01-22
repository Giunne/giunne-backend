package com.giunne.itemservice.domain.item.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아이템명
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemName {
    @Column(name = "item_name", nullable = false)
    private String value;

    public ItemName(String value) {
        this.value = value;
    }

    public static ItemName from(final String value) {
        return new ItemName(value);
    }
}
