package com.giunne.itemservice.domain.orders.application;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import com.giunne.commonservice.infra.external.domain.member.client.MemberInfoClient;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.InsertInventoryItemRequestDto;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.Response;
import com.giunne.itemservice.domain.gacha.domain.WeightedRandom;
import com.giunne.itemservice.domain.item.application.interfaces.ItemRepository;
import com.giunne.itemservice.domain.item.domain.Item;
import com.giunne.itemservice.domain.item.domain.type.GachaType;
import com.giunne.itemservice.domain.orders.api.response.GetItemOrderGachaResponseDto;
import com.giunne.itemservice.domain.orders.application.dto.response.GachaTypeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final ItemRepository itemRepository;
    public final MemberInfoClient memberInfoClient;

    public List<GachaTypeResponseDto> getGachaTypes() {
        return Stream.of(GachaType.values())
                .map(i ->
                        {
                            List<String> imageList = itemRepository.findByGachaTypeIamgeList(i);
                            return new GachaTypeResponseDto(i, imageList);
                        }
                )
                .collect(Collectors.toList());
    }

    public GetItemOrderGachaResponseDto orderGacha(MemberPrincipal memberPrincipal, String type) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        List<Long> myInventory = memberInfoClient.findMyInventory(memberPrincipal.getPlayerId()).value();

        GachaType gachaType = GachaType.from(type);
        List<GetItemOrderGachaResponseDto> itemList = itemRepository.findByGachaType(gachaType,myInventory);

        if (itemList.isEmpty()) {
            throw new BusinessException(ErrorCode.MAX_INVENTORY);
        }

        WeightedRandom weightedRandom = new WeightedRandom(gachaType, itemList);

        GetItemOrderGachaResponseDto random = weightedRandom.getRandom();

        InsertInventoryItemRequestDto insertInventoryItemRequestDto = InsertInventoryItemRequestDto.builder()
                .id(random.getId())
                .itemName(random.getItemName())
                .categoryId(random.getCategoryId())
                .playerId(memberPrincipal.getPlayerId())
                .build();


        memberInfoClient.insertInventory(insertInventoryItemRequestDto);

        return random;
    }
}
