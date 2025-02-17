package com.giunne.itemservice.domain.category.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "카테고리 하위 이동 요청DTO")
public record MoveWithSubCategoryRequestDto(
        @Schema(
                description = "현재 카테고리 번호",
                example = "1"
        )
        Long currentId,
        @Schema(
                description = "하위 카테고리 번호",
                example = "1"
        )
        Long moveId
) {

}
