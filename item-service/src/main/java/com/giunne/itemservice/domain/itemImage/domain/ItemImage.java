package com.giunne.itemservice.domain.itemImage.domain;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.itemImage.domain.type.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ItemImage {
    private Long id;
    private FileName fileName; // 파일명
    private FileSize fileSize; // 파일 사이즈
    private FileUrl fileUrl; // 파일 URL
    private Item item;
    private IsRepresent isRepresent;
    private Level level;

    public void changeItem(Item newItem){
        this.item = newItem;
    }
}
