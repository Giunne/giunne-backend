package com.giunne.commonservice.infra.external.domain.member.client.dto.response;

import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "나의 포인트 조회 응답DTO")
public class GetMyPointResponseDto {
    @Schema(
            description = "포인트",
            example = "1"
    )
    Long point;
}
