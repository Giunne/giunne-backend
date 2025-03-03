package com.giunne.memberservice.domain.inventory.application;

import com.giunne.commonservice.infra.external.domain.item.client.ItemInfoClient;
import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetItemResponseDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.avatar.application.AvatarService;
import com.giunne.memberservice.domain.avatar.application.interfaces.AvatarRepository;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.inventory.api.request.GetItemPageRequestDto;
import com.giunne.memberservice.domain.inventory.application.interfaces.InventoryRepository;
import com.giunne.memberservice.domain.inventory.domain.Inventory;
import com.giunne.memberservice.domain.inventory.domain.type.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final AvatarRepository avatarRepository;
    private final ItemInfoClient itemInfoClient;

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

    public Response<PaginationModel<GetItemResponseDto>> findMyInventoryItems(MemberPrincipal memberPrincipal, GetItemPageRequestDto dto) {

        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        Avatar avatar = avatarRepository.findById(memberPrincipal.getPlayerId());
        List<Inventory> inventoryByAvatar = inventoryRepository.findInventoryByAvatar(avatar);
        List<Long> itemLIst = inventoryByAvatar.stream().map(i -> i.getItemInfo().getItemNo()).toList();

        GetItemsRequestDto getItemsRequestDto = GetItemsRequestDto.builder()
                .itemIds(itemLIst)
                .categoryId(dto.getCategoryId())
                .build();

         return itemInfoClient.findByItems(getItemsRequestDto);
    }

}
