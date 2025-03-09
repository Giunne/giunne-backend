package com.giunne.questservice.domain.questState.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "회원별 퀘스트 상태 수정 요청DTO")
public record UpdateQuestStateRequestDto(
        @Schema(
                description = "퀘스트 상태 번호",
                example = "1"
        )
        Long questStateId,
        @Schema(
                description = "퀘스트 진행상태",
                example = "CONFIRM"
        )
        String questProgress
) {
}
