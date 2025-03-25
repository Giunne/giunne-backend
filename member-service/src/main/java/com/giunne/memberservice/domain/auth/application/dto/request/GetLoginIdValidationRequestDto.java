package com.giunne.memberservice.domain.auth.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Schema(description = "회원가입 검증 요청DTO")
public record GetLoginIdValidationRequestDto(
        @Schema(
                description = "로그인ID",
                example = "test"
        )
        String loginId
) {
}
