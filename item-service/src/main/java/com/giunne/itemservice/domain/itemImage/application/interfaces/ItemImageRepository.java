package com.giunne.itemservice.domain.itemImage.application.interfaces;

import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.itemImage.domain.ItemImage;

import java.util.List;

public interface ItemImageRepository {
    List<ItemImage> saveAll(List<ItemImage> itemImageList);
    void updateItemImage(List<ItemImage> itemImageList);
    List<ItemImage> findByItemImageIdList(List<Long> itemImageList);
    List<ItemImage> findByItemId(Long itemId);

}
