package com.giunne.memberservice.domain.notice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "공지 조회 응답DTO")
public class GetNoticeResponseDto {
    @Schema(description = "알림 ID",
            example = "1000")
    private Long id;
    @Schema(description = "작성자 ID",
            example = "1000")
    private Long writerId;
    @Schema(description = "공지 제목",
            example = "공지 제목")
    private String title;
    @Schema(description = "공지 내용",
            example = "공지 내용")
    private String content;
    @Schema(
            description = "생성날짜"
    )
    LocalDateTime createTime;
    @Schema(
            description = "수정날짜"
    )
    LocalDateTime updateTime;
    @Schema(
            description = "읽음 유무",
            example = "true")
    Boolean isRead;
}
