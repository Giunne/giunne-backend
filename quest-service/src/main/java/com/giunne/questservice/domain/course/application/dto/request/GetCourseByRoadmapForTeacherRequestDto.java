package com.giunne.questservice.domain.course.application.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "특정 학생 로드맵별 코스 조회 요청DTO")
public class GetCourseByRoadmapForTeacherRequestDto {
    @Parameter(
            description = "로드맵 ID",
            example = "1"
    )
    @NotNull
    private Long roadmapId;
    @Parameter(
            description = "플레이어 ID",
            example = "1"
    )
    @NotNull
    private Long playerId;
}
