package com.giunne.commonservice.infra.external.domain.member.client.dto.request;

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
@Schema(description = "특정 아바타 정보 조회 요청DTO")
public class GetAvatarProfileRequestDto {

    @Parameter(
            description = "플레이어 번호",
            example = "1"
    )
    @NotNull
    private Long playerId;
}
