package com.giunne.itemservice.domain.category.domain;

import com.giunne.itemservice.domain.category.domain.type.CategoryName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Category {
    private Long id; // 카테고리 번호
    private CategoryName categoryName; // 카테고리명
    private Boolean isLeaf = false;
    private Boolean isRoot = false;
}
