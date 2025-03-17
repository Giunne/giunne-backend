package com.giunne.commonservice.infra.external.domain.member.client;

import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileListRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.GetAvatarProfileRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.request.InsertInventoryItemRequestDto;
import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import com.giunne.commonservice.ui.Response;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(url = "${app.apiUrl.member-service}", name = "memberInfoClient")
public interface MemberInfoClient {

    @GetMapping(value = "/inventory/my-inventory/{playerId}")
    Response<List<Long>> findMyInventory(@PathVariable("playerId") Long playerId);

    @PostMapping(value = "/inventory")
    Response<String> insertInventory(@RequestBody InsertInventoryItemRequestDto dto);

    @GetMapping(value = "/avatar/avatar-profile")
    Response<GetMyRecreationAvatarResponseDto > getAvatarProfileInfo(@ParameterObject GetAvatarProfileRequestDto dto);

    @GetMapping(value = "/avatar/avatar-profiles")
    Response<List<GetMyRecreationAvatarResponseDto> > getAvatarProfileListInfo(@RequestParam("playerId") List<Long> playerId);
}
