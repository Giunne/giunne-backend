package com.giunne.questservice.domain.questState.application.dto.request;

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
@Schema(description = "인증 상황 조회 요청DTO")
public class GetProgressQuestRequestDto {
    @Parameter(
            description = "로드맵 ID",
            example = "1"
    )
    @NotNull
    private Long roadmapId;
}
