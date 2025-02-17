package com.giunne.itemservice.domain.item.application.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.itemservice.domain.item.application.dto.request.GetItemPageRequestDto;
import com.giunne.itemservice.domain.item.application.dto.response.GetItemPageResponseDto;
import com.giunne.itemservice.domain.item.domain.Item;

import java.util.List;

public interface ItemRepository {
    List<Item> saveAll(List<Item> itemList);
    void updateItem(List<Item> itemList);
    List<Item> findByItemIdList(List<Long> itemIdList);
    Item findById(Long id);

    PaginationModel<GetItemPageResponseDto> findByCategory(GetItemPageRequestDto dto);
}
