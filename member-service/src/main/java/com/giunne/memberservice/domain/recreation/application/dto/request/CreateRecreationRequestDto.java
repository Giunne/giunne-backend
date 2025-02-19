package com.giunne.memberservice.domain.recreation.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "레크레이션 생성 요청DTO")
public record  CreateRecreationRequestDto(
        @Schema(
                description = "레크레이션 이름",
                example = "기능성 운동 교실"
        )
        String recreationName,
         @Schema(
                 description = "기수번호",
                 example = "1"
         )
          Long baseNumber
) {

}
