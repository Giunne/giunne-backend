package com.giunne.memberservice.domain.inventory.domain.type;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 아이템 점보 유무
 */

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemInfo {

    @Column(name = "item_no", nullable = false)
    private Long itemNo; // 아이템번호

    @Column(name = "item_name", nullable = false)
    private String itemName; // 아이템명

    @Column(name = "category_no", nullable = false)
    private Long categoryNo; // 아이템번호

    @Builder
    public ItemInfo(Long itemNo, String itemName, Long categoryNo) {
        this.itemNo = itemNo;
        this.itemName = itemName;
        this.categoryNo = categoryNo;
    }

    public static ItemInfo from(Long itemNo, String itemName, Long categoryNo) {
        return new ItemInfo(itemNo, itemName, categoryNo);
    }
}
