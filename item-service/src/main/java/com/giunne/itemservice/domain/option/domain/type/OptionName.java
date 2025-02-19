package com.giunne.itemservice.domain.option.domain.type;


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
public class OptionName {
    @Column(name = "option_name", nullable = false)
    private String itemName;

    public OptionName(String value) {
        this.itemName = value;
    }

    public static OptionName from(final String value) {
        return new OptionName(value);
    }
}
