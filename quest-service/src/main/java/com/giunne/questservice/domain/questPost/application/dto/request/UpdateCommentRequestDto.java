package com.giunne.questservice.domain.questPost.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCommentRequestDto(
        @Schema(
                description = "댓글 내용",
                example = "댓글 내용"
        )
        String content
) {

}
