package com.giunne.itemservice.domain.optionCategory.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아이템 옵션명
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionCategoryName {
    @Column(name = "option_category_name", nullable = false)
    private String itemName;

    public OptionCategoryName(String value) {
        this.itemName = value;
    }

    public static OptionCategoryName from(final String value) {
        return new OptionCategoryName(value);
    }
}
