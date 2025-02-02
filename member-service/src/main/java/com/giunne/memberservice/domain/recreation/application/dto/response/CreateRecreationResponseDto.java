package com.giunne.memberservice.domain.recreation.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateRecreationResponseDto(
        @Schema(
                description = "레크레이션 번호",
                example = "1"
        )
        Long id,
        @Schema(
                description = "레크레이션 이름",
                example = "기능성 운동 교실"
        )
        String recreationName,
        @Schema(
                description = "레크레이션 코드",
                example = "123456789012345(15자리)"
        )
        String recreationCode,
        @Schema(
                description = "기수번호",
                example = "1"
        )
        Long baseNumber
) {
}
