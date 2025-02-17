package com.giunne.itemservice.domain.item.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아이템설명
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemDescription {
    @Column(name = "item_description", nullable = false)
    private String description;

    public ItemDescription(String description) {this.description = description;}

    public static ItemDescription from(final String value) {
        return new ItemDescription(value);
    }

}
