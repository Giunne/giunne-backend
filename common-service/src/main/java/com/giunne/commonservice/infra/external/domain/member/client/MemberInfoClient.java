package com.giunne.commonservice.infra.external.domain.member.client;

import com.giunne.commonservice.infra.external.domain.member.client.dto.request.*;
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
    Response<GetMyRecreationAvatarResponseDto> getAvatarProfileInfo(@RequestParam("playerId") Long playerId);

    @GetMapping(value = "/avatar/avatar-profiles")
    Response<List<GetMyRecreationAvatarResponseDto>> getAvatarProfileListInfo(@RequestParam("playerId") List<Long> playerId);

    @GetMapping(value = "/avatar/increase-experience")
    Response<String> increaseExperience(@RequestPart(name = "playerId") Long playerId,
                                        @RequestPart(name = "exp") Long exp);

    @GetMapping(value = "/avatar/increase-point")
    Response<String> increasePoint(@RequestPart(name = "playerId") Long playerId,
                                   @RequestPart(name = "point") Long point);

    @GetMapping(value = "/avatar/decrease-point")
    Response<String> decreasePoint(@RequestPart(name = "playerId") Long playerId,
                                   @RequestPart(name = "point") Long point);

}
