package com.giunne.memberservice.domain.auth.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원가입 검증 응답DTO")
public class GetLoginIdValidationResponseDto {
    @Schema(
            description = "중복 확인",
            example = "true"
    )
    Boolean isPresent;
}
