package com.giunne.questservice.domain.questPost.application.dto.response;

import com.giunne.commonservice.infra.external.domain.member.client.dto.response.GetMyRecreationAvatarResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "퀘스트 게시물 응답DTO")
public class GetPostListResponseDto {
    @Schema(
            description = "게시물 정보 리스트"
    )
    List<GetPostDetailResponseDto> postInfoList;
    @Schema(
            description = "플레이어 프로필 정보"
    )
    GetMyRecreationAvatarResponseDto playerInfo;

}
