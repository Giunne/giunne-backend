package com.giunne.commonservice.infra.external.domain.member.client.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class GetFcmTokenResponseDto {
    @Schema(
            description = "fcm토큰 아이디"
    )
    private Long id;
    @Schema(
            description = "회원 번호"
    )
    private Long memberId;
    @Schema(
            description = "토큰정보"
    )
    private String token;
}
