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
@Schema(description = "레크레이션내 학생정보 응답DTO")
public class GetMyRecreationAvatarResponseDto {
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
            description = "레크레이션 코드",
            example = "123456789012345(15자리)"
    )
    String recreationCode;
    @Schema(
            description = "선생님 회원 번호",
            example = "123"
    )
    Long teacherId;
    @Schema(
            description = "선생님 로그인ID",
            example = "LoginID"
    )
    String teacherLoginId;
    @Schema(
            description = "선생님명",
            example = "허태식"
    )
    String teacherName;

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
            description = "필요 경험치")
    Long needExp;
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
            description = "착용중인 아이템 번호 List",
            hidden = true
    )
    List<Long> wearingItemIds;
    @Schema(
            description = "착용중인 아이템 List"
    )
    List<GetWearingItemResponseDto> wearingItems;
}
