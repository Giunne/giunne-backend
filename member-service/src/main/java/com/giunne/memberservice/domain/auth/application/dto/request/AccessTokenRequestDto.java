package com.giunne.memberservice.domain.auth.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Schema(description = "access token요청 DTO")
public class AccessTokenRequestDto {
    @Nullable
    @Schema(description = "플레이어 ID"
            , example = "1"
            , nullable = true
    )
    private Long playerId;
}
