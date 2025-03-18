package com.giunne.questservice.domain.questPost.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "게시물 조회DTO")
public record GetPostRequestDto (
        @Schema(
                description = "플레이어 번호",
                example = "1"
        )
        Long playerId,
        @Schema(
                description = "퀘스트 번호",
                example = "1"
        )
        Long questId
) {
}