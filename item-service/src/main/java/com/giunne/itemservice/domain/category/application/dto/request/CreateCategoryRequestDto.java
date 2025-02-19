package com.giunne.itemservice.domain.category.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "카테고리 생성 요청DTO")
public record CreateCategoryRequestDto(
        @Schema(
                description = "카테고리명",
                example = "머리"
        )
        String categoryName,
        @Schema(
                description = "부모 카테고리 번호",
                example = "1"
        )
        Long parentsId
) {

}
