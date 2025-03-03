package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "코스정보 수정 요청DTO")
public record UpdateCourseInfoRequestDto(
        @Schema(
                description = "코스 번호",
                example = "1"
        )
        @NotNull(message = "코스 번호는 필수값입니다.")
        Long id,
        @Schema(
                description = "코스 설명",
                example = "설명",
                nullable = false

        )
        @NotNull(message = "코스 설명는 필수값입니다.")
        String description,
        @Schema(
                description = "운동 설명",
                nullable = false
        )
        @NotNull(message = "코스 번호는 필수값입니다.")
        String trainingDescription,
        @Schema(
                description = "보상 포인트",
                nullable = false
        )
        @NotNull(message = "보상 포인트는 필수값입니다.")
        Long rewardPoint,
        @Schema(
                description = "보상 경험치",
                nullable = false
        )
        @NotNull(message = "보상 경험치는 필수값입니다.")
        Long rewardExp,
        @Schema(
                description = "가이드 URL",
                nullable = false
        )
        @NotNull(message = "가이드 URL는 필수값입니다.")
        String guideUrl
) {
}
