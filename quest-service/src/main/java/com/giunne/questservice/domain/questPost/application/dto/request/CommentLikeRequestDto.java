package com.giunne.questservice.domain.questPost.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CommentLikeRequestDto(
        @Schema(
                description = "게시물 번호",
                example = "1"
        )
        Long postId
) {

}
