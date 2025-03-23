package com.giunne.questservice.domain.quest.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "채점하기 요청DTO (선생님용)")
public record CertificateRequestDto(
        @Schema(
                description = "게시물ID",
                example = "1"
        )
        Long questPostId,
        @Schema(
                description = "통과/불통과 여부",
                example = "true"
        )
        Boolean isPass,
        @Schema(
                description = "추가 동작 수행 여부",
                example = "false",
                nullable = true
        )
        Boolean hasExtraPoints,
        @Schema(
                description = "별",
                example = "3",
                nullable = true
        )
        Long starPoint
        ) {

        public CertificateRequestDto(Long questPostId, Boolean isPass, Boolean hasExtraPoints, Long starPoint) {
                this.questPostId = questPostId;
                this.isPass = isPass;
                this.hasExtraPoints = hasExtraPoints != null ? hasExtraPoints : false;
                this.starPoint = starPoint != null ? starPoint : 0L;
        }
}
