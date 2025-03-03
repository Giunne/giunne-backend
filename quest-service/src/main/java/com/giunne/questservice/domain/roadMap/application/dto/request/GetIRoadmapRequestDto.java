package com.giunne.questservice.domain.roadMap.application.dto.request;

import com.giunne.commonservice.domain.common.Pageable;
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
@Schema(description = "로드맵 조회 요청DTO")
public class GetIRoadmapRequestDto {
    @Parameter(
            description = "레크레이션 ID",
            example = "1"
    )
    @NotNull
    private Long recreationId;
}
