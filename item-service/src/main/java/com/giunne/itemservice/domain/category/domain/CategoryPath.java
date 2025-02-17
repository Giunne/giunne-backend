package com.giunne.itemservice.domain.category.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryPath {
    private Long id; // 클로저 테이블 번호
    private Category parents;
    private Category child;
}
