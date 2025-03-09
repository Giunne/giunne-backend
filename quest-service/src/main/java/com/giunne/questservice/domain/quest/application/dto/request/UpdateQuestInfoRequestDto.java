package com.giunne.questservice.domain.quest.application.dto.request;

import com.giunne.questservice.domain.quest.domain.Quest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import javax.management.Query;

@Builder
@Schema(description = "퀘스트정보 수정 요청DTO")
public record UpdateQuestInfoRequestDto(
        @Schema(
                description = "퀘스트 ID",
                example = "1"
        )
        @NotNull(message = "퀘스트 번호는 필수값입니다.")
        Long id,
        @Schema(
                description = "퀘스트 설명",
                example = "퀘스트 설명",
                nullable = false
        )
        @NotNull(message = "코스 설명은 필수값입니다.")
        String questDescription,
        @Schema(
                description = "운동 설명",
                example = "운동 설명",
                nullable = false
        )
        @NotNull(message = "운동 설명은 필수값입니다.")
        String trainingDescription,
        @Schema(
                description = "보상 포인트",
                example = "3",
                nullable = false
        )
        @NotNull(message = "보상 포인트는 필수값입니다.")
        Long rewardPoint,
        @Schema(
                description = "보상 경험치",
                example = "3",
                nullable = false
        )
        @NotNull(message = "보상 경험치는 필수값입니다.")
        Long rewardExp,
        @Schema(
                description = "가이드 URL",
                example = "https://www.youtube.com/",
                nullable = false
        )
        @NotNull(message = "가이드 URL는 필수값입니다.")
        String guideUrl
) {
}
