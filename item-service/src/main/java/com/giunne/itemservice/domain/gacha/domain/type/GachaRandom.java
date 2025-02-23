package com.giunne.itemservice.domain.gacha.domain.type;

import com.giunne.commonservice.domain.item.ItemGrade;
import com.giunne.itemservice.domain.item.domain.type.ItemName;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class GachaRandom {

    private Long id;
    private ItemName itemName; // 아이템명
    private Long weight;
    private ItemGrade itemGrade;

}