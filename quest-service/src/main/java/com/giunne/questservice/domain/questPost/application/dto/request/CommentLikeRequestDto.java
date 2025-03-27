package com.giunne.questservice.domain.questPost.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentLikeRequestDto(
        @Schema(
                description = "게시물 번호",
                example = "1"
        )
        Long postId,
        @Schema(
                description = "경험치 보상",
                example = "1"
        )
        Long rewardExp,
        @Schema(
                description = "포인트 보상",
                example = "1"
        )
        Long rewardPoint
) {
}
