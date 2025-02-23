package com.giunne.memberservice.domain.inventory.domain;

import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.inventory.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Inventory {
    private Long id; // 인벤토리 번호
    private Quantity quantity; // 수량
    private SortSeq sortSeq; // 정렬순서
    private IsWear isWear; // 착용유무
    private HasItem hasItem; // 보유유무
    private ItemInfo itemInfo; // 아이템정보
    private Avatar avatar; // 아바타
}
