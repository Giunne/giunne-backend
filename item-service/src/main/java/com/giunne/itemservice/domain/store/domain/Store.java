package com.giunne.itemservice.domain.store.domain;

import com.giunne.itemservice.domain.store.domain.type.StoreName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Store {
    private Long id; // 상점 번호
    private StoreName storeName; // 상점명
}
