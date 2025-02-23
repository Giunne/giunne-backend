package com.giunne.memberservice.domain.inventory.application;

import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.inventory.application.interfaces.InventoryRepository;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.inventory.domain.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public Inventory insertInventory(Avatar avatar, ItemInfoResponseDto itemInfoResponseDto){

        Inventory inventory = Inventory.builder()
                .quantity(Quantity.from(1L))
                .sortSeq(SortSeq.from(1L))
                .isWear(IsWear.from(true))
                .hasItem(HasItem.from(true))
                .avatar(avatar)
                .itemInfo(ItemInfo.builder()
                        .categoryNo(itemInfoResponseDto.getCategoryId())
                        .itemName(itemInfoResponseDto.getItemName())
                        .itemNo(itemInfoResponseDto.getId())
                        .build())
                .build();


        return inventoryRepository.insertInventory(inventory);
    }

}
