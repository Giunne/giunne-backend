package com.giunne.itemservice.domain.item.domain;

import com.giunne.itemservice.domain.category.domain.Category;
import com.giunne.itemservice.domain.item.domain.type.*;
import com.giunne.itemservice.domain.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Item {
    private Long id;
    private ItemName itemName; // 아이템명
    private ItemDescription itemDescription;
    private Price price; // 기격
    private NeedLevel needLevel; // 착용가능 레벨
    private SortSeq sortSeq; // 순서번호
    private Stock stock; // 재고
    private Category category;
    private Store store;
    private ItemGrade itemGrade;
}
