package com.giunne.itemservice.domain.category.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record CategoryMapResponseDto(
        @Schema(
                description = "카테고리 정보"
        )
        Map<Integer, List<CategoryResponseDto>> categoryData
) {
}
