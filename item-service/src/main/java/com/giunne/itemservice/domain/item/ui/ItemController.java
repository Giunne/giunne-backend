package com.giunne.itemservice.domain.item.ui;

import com.giunne.commonservice.infra.external.domain.item.client.dto.request.GetWearingItemsRequestDto;
import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.commonservice.ui.Response;
import com.giunne.itemservice.domain.item.application.ItemCsvService;
import com.giunne.itemservice.domain.item.application.ItemService;
import com.giunne.itemservice.domain.item.application.dto.request.GetItemPageRequestDto;
import com.giunne.itemservice.domain.item.application.dto.response.GetItemPageResponseDto;
import com.giunne.itemservice.domain.item.application.dto.response.ItemInfoResponseDto;
import com.giunne.itemservice.domain.item.domain.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "아이템 관리", description = "아이템 조회 및 저장")
@RestController
@RequestMapping("/v1/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemCsvService itemCsvService;
    private final ItemService itemService;

    @PostMapping("/upload-csv-file")
    private Response<String> uploadCsvFile(@RequestParam("file") MultipartFile file) {
        itemCsvService.itemCsvToEntity(file);
        return Response.ok("저장완료");
    }

    @Operation(summary = "카테고리별 아이템 검색", description = """
            ## 기능설명
            * 아이템정보를 검색합니다.
            ---
            ## 상세설명
            * 카테고리 ID(categoryId)를 이용하여 상품정보를 검색합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/category")
    public Response<PaginationModel<GetItemPageResponseDto>> findByNameLike(@ParameterObject GetItemPageRequestDto dto) {
        PaginationModel<GetItemPageResponseDto> items = itemService.findByCategory(dto);
        return Response.ok(items);
    }

    @Operation(summary = "아이템 번호 검색", description = """
            ## 기능설명
            * 아이템정보를 검색합니다.
            ---
            ## 상세설명
            * 아이템 ID(itemId)를 이용하여 상품정보를 검색합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @GetMapping("/{itemId}")
    public Response<ItemInfoResponseDto> findByItemId(@PathVariable(name = "itemId") Long itemId) {
        ItemInfoResponseDto item = itemService.findById(itemId);
        return Response.ok(item);
    }


    @Operation(summary = "장착중인 아이템 미리보기 검색", description = """
            ## 기능설명
            * 장착중인 아이템을 미리확인 검색합니다.
            ---
            ## 상세설명
            * 아이템 ID 리스트(itemIds)를 이용하여 상품정보를 검색합니다.
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping("/wearing")
    public Response<List<GetWearingItemResponseDto>> findWearingItems(@RequestBody GetWearingItemsRequestDto dto) {
        List<GetWearingItemResponseDto> wearingItems = itemService.findWearingItems(dto);
        return Response.ok(wearingItems);
    }
}
