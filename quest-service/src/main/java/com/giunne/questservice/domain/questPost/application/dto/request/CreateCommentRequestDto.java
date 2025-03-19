package com.giunne.questservice.domain.questPost.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCommentRequestDto(
        @Schema(
                description = "게시물 번호",
                example = "1"
        )
        Long postId,
        @Schema(
                description = "댓글 내용",
                example = "댓글 내용"
        )
        String content
) {

}

