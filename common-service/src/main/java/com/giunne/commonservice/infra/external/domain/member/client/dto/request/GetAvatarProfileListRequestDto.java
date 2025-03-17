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
@Schema(description = "특정 아바타 정보 조회 요청DTO")
public class GetAvatarProfileListRequestDto {

    @Parameter(
            description = "플레이어 번호"
    )
    @NotNull
    private List<Long> playerId;
}
