package com.giunne.memberservice.domain.inventory.ui;


import com.giunne.commonservice.infra.external.domain.member.client.dto.request.InsertInventoryItemRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetItemResponseDto;
import com.giunne.commonservice.principal.AuthPrincipal;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.memberservice.domain.inventory.api.request.GetItemPageRequestDto;
import com.giunne.memberservice.domain.inventory.application.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "인벤토리 관리", description = "인벤토리 생성 및 조회")
@RestController
@RequestMapping("/v1/api/member/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "카테고리별 인벤토리 조회", description = """
            ## 기능설명
            * 카테고리별 인벤토리 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/category")
    public Response<PaginationModel<GetItemResponseDto>> findMyInventoryItems(@AuthPrincipal @Parameter(hidden = true) MemberPrincipal memberPrincipal, @ParameterObject GetItemPageRequestDto dto ) {
        Response<PaginationModel<GetItemResponseDto>> myInventoryItems = inventoryService.findMyInventoryItems(memberPrincipal, dto);
        return myInventoryItems;
    }

    @Operation(summary = "플레이어가 가지고있는 아이템 번호 리스트 조회", description = """
            ## 기능설명
            * 플레이어가 가지고있는 아이템 번호 리스트 조회
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/my-inventory/{playerId}")
    public Response<List<Long>> findMyInventory(@PathVariable("playerId") Long playerId) {
        List<Long> myInventoryItems = inventoryService.findMyInventoryItems(playerId);
        return Response.ok(myInventoryItems);
    }

    @Operation(summary = "인벤토리 아이템 저장", description = """
            ## 기능설명
            * 인벤토리 아이템 저장
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    public Response<String> insertInventory(@RequestBody InsertInventoryItemRequestDto dto ) {
        inventoryService.insertInventory(dto);
        return Response.ok("성공");
    }
}
