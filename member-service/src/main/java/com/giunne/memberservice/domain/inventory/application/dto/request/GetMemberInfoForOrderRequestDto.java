package com.giunne.memberservice.domain.inventory.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "아바타정보 조회 요청DTO(상품 구매용)")
public record GetMemberInfoForOrderRequestDto(
        Long avatarId
) {
}
