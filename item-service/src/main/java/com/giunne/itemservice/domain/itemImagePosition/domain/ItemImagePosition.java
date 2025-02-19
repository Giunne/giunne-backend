package com.giunne.itemservice.domain.itemImagePosition.domain;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;
import com.giunne.itemservice.domain.itemImagePosition.domain.type.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ItemImagePosition {
    private Long id;
    private Position position; // 위치
    private Long avatarNo; // 캐릭터 번호
    private ItemImage itemImage;
    private Long level;
    private Item item;

    public void changItemImage(ItemImage newItemImage){
        this.itemImage = newItemImage;
    }

}
