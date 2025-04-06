package com.giunne.memberservice.domain.notice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "공지사항 수정 요청DTO")
public record UpdateNoticeRequestDto(
        @Schema(
                description = "퀘스트 ID",
                example = "1"
        )
        @NotNull(message = "퀘스트 번호는 필수값입니다.")
        Long id,
        @Schema(
                description ="공지 제목",
                example = "공지 제목",
                nullable = false
        )
        @NotNull(message = "코스 설명은 필수값입니다.")
        String title,
        @Schema(
                description = "공지 내용",
                example = "공지 내용",
                nullable = false
        )
        @NotNull(message = "코스 설명은 필수값입니다.")
        String content
) {

}
