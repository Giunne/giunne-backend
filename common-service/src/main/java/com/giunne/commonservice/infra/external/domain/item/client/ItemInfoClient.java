package com.giunne.commonservice.infra.external.domain.item.client;

import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.commonservice.ui.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@FeignClient(url = "${app.apiUrl.item-service}", name = "itemInfoClient")
public interface ItemInfoClient {

    @GetMapping(value = "/{itemId}")
    Response<ItemInfoResponseDto> requestItemInfo(@PathVariable("itemId") Long itemId);

    @PostMapping(value = "/wearing")
    Response<List<GetWearingItemResponseDto>> requestFindWearingItems(@RequestBody GetWearingItemsRequestDto dto);
}
