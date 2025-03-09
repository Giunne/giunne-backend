package com.giunne.questservice.domain.roadMap.application.dto.response;

import com.giunne.questservice.domain.roadMap.domain.type.RoadMapType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "로드맵 조회 응답DTO")
public class GetIRoadmapResponseDto {
    @Schema(
            description = "로드맵 ID)",
            example = "1"
    )
    private Long id; // 로드맵

    @Schema(
            description = "제목",
            example = "로드맵"
    )
    private String title; // 제목
    @Schema(
            description = "설명",
            example = "로드맵 설명"
    )
    private String description; // 설명
    @Schema(
            description = "로드맵 타입"
    )
    private RoadMapType roadMapType;
}
