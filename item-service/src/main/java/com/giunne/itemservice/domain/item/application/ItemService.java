package com.giunne.itemservice.domain.item.application;

import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.itemservice.domain.item.application.dto.request.GetItemPageRequestDto;
import com.giunne.itemservice.domain.item.application.dto.response.GetItemPageResponseDto;
import com.giunne.itemservice.domain.item.application.dto.response.ItemInfoResponseDto;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import com.giunne.itemservice.domain.item.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public PaginationModel<GetItemPageResponseDto> findByCategory(GetItemPageRequestDto dto) {
        return itemRepository.findByCategory(dto);
    }

    public List<GetWearingItemResponseDto> findWearingItems(GetWearingItemsRequestDto dto) {
        return itemRepository.findWearingItems(dto);
    }

    public ItemInfoResponseDto findById(Long itemId) {
        Item item = itemRepository.findById(itemId);

        return ItemInfoResponseDto.builder()
                .id(item.getId())
                .itemName(item.getItemName().getItemName())
                .itemDescription(item.getItemDescription().getDescription())
                .price(item.getPrice().getValue())
                .needLevel(item.getNeedLevel().getValue())
                .sortSeq(item.getSortSeq().getValue())
                .categoryId(item.getCategory().getId())
                .itemGrade(item.getItemGrade())
                .build();
    }

}
