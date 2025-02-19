package com.giunne.itemservice.domain.store.appliaction.dto.reqeuest;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "상점 생성 요청DTO")
public record CreateStoreRequestDto (
        @Schema(
                description = "상점멷",
                example = "기운내 상점"
        )
        String storeName
){
}
