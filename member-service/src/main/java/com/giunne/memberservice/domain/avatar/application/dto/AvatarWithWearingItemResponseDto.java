package com.giunne.memberservice.domain.avatar.application.dto;

import com.giunne.commonservice.infra.external.domain.item.client.dto.response.GetWearingItemResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "아바타 정보 응답DTO")
public class AvatarWithWearingItemResponseDto {
    @Schema(
            description = "플레이어 ID",
            example = "1"
    )
    Long id;
    @Schema(
            description = "닉네임",
            example = "기능성 왕"
    )
    String nickname;
    @Schema(
            description = "레크레이션 ID",
            example = "1234"
    )
    Long recreationId;
    @Schema(
            description = "레크레이션 ID",
            example = "1234"
    )
    String recreationName;
    @Schema(
            description = "경험치",
            example = "1"
    )
    Long exp;
    @Schema(
            description = "레벨",
            example = "1"
    )
    Long level;
    @Schema(
            description = "포인트",
            example = "0"
    )
    Long point;
    @Schema(
            description = "캐릭터 번호",
            example = "1"
    )
    Long characterNo;
    @Schema(
            description = "착용중인 아이템 번호 List"
    )
    List<Long> wearingItemIds;
    @Schema(
            description = "착용중인 아이템 List"
    )
    List<GetWearingItemResponseDto> wearingItems;
}
