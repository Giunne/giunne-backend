package com.giunne.itemservice.domain.category.domain.type;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리명
 */
@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryName {
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    public CategoryName(String value) {
        this.categoryName = value;
    }

    public static CategoryName from(final String value) {
        return new CategoryName(value);
    }
}
