package com.giunne.itemservice.domain.store.ui;

import com.giunne.itemservice.domain.store.appliaction.StoreService;
import com.giunne.itemservice.domain.store.appliaction.dto.reqeuest.CreateStoreRequestDto;
import com.giunne.itemservice.domain.store.appliaction.dto.response.CreateStoreResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상저 관리", description = "상저 생성 및 조회")
@RestController
@RequestMapping("/v1/api/items/store")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @Operation(summary = "상점 생성", description = """
            ## 기능설명
            * 상점 생성
            ---
            """, responses = {
            @ApiResponse(responseCode = "200", description = "성공")
    })
    @PostMapping
    private CreateStoreResponseDto createStore(@RequestBody CreateStoreRequestDto dto) {
        return storeService.createStore(dto);
    }

}
