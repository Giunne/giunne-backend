package com.giunne.memberservice.domain.notice.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "공지사항 저장 요청DTO")
public record CreateNoticeRequestDto(
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
        String content,
         @Schema(
                 description = "레크레이션 ID",
                 example = "10",
                 nullable = false
         )
        @NotNull(message = "레크레이션 ID는 필수값입니다.")
        Long recreationId
) {

}
