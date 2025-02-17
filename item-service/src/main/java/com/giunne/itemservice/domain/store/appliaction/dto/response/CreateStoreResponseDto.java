package com.giunne.itemservice.domain.store.appliaction.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateStoreResponseDto(
        @Schema(
                description = "상점 번호",
                example = "1"
        )
        Long id,
        @Schema(
                description = "상점멷",
                example = "기운내 상점"
        )
        String storeName
) {
}
