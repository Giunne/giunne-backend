package com.giunne.itemservice.domain.itemImagePosition.application.interfaces;

import com.giunne.itemservice.domain.itemImagePosition.domain.ItemImagePosition;

import java.util.List;

public interface ItemImagePositionRepository {
    List<ItemImagePosition> saveAll(List<ItemImagePosition> itemImagePositions);
}
