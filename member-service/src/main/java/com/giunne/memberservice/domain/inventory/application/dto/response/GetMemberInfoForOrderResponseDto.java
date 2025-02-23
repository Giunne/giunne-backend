package com.giunne.memberservice.domain.inventory.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "아바타정보 조회 응답DTO(상품 구매용)")
public record GetMemberInfoForOrderResponseDto(
        @Schema(
                description = "현재 보유 포인트 ",
                example = "100"
        )
        Long point,
        @Schema(
                description = "인벤토리 상품 ID LIST"
        )
        List<Long> itemIdList
) {
}
