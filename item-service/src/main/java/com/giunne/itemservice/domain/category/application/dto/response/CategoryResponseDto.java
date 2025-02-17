package com.giunne.itemservice.domain.category.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CategoryResponseDto(
        @Schema(
                description = "카테고리 번호",
                example = "1"
        )
        Long id,
        @Schema(
                description = "카테고리명",
                example = "모자"
        )
        String categoryName,
        @Schema(
                description = "최상위(root) 여부",
                example = "true"
        )
        Boolean isRoot,
        @Schema(
                description = "말단(leaf)여부",
                example = "true"
        )
        Boolean isLeaf
) {
}
