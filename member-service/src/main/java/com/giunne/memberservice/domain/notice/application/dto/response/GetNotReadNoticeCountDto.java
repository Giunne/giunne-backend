package com.giunne.memberservice.domain.notice.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "읽지 않은 메시지 수 응답DTO")
public class GetNotReadNoticeCountDto {
    @Schema(description = "읽지 않은 메시지 수",
            example = "10")
    private Long count;
}
