package com.giunne.commonservice.infra.external.domain.member.client;

import com.giunne.commonservice.infra.external.domain.item.client.dto.response.ItemInfoResponseDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.InsertInventoryItemRequestDto;
import com.giunne.commonservice.ui.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(url = "${app.apiUrl.member-service}", name = "memberInfoClient")
public interface MemberInfoClient {

    @GetMapping(value = "/inventory/my-inventory/{playerId}")
    Response<List<Long>> findMyInventory(@PathVariable("playerId") Long playerId);

    @PostMapping(value = "/inventory")
    Response<String> insertInventory(@RequestBody InsertInventoryItemRequestDto dto);
}
