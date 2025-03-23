package com.giunne.commonservice.infra.external.domain.member.client.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "특정 아바타 경험치 변경 요청DTO")
public class UpdateExpRequestDto {
    @Parameter(
            description = "플레이어 번호"
    )
    @NotNull
    private Long playerId;
    @Parameter(
            description = "경험치 증가량"
    )
    @NotNull
    private Long exp;
}
