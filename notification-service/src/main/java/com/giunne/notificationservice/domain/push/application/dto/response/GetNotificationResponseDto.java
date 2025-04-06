package com.giunne.notificationservice.domain.push.application.dto.response;

import com.giunne.notificationservice.domain.push.domain.type.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "알림함 조회 응답DTO")
public class GetNotificationResponseDto {
    @Schema(description = "알림 ID",
            example = "1000")
    private Long id;
    @Schema(description = "수신자 ID",
            example = "1000")
    private Long targetId;
    @Schema(description = "송신자 ID",
            example = "1000")
    private Long senderId;
    @Schema(description = "알림 제목",
            example = "알림 제목")
    private String title;
    @Schema(description = "알림 내용",
            example = "알림 내용")
    private String content;
    private NotificationType notificationType;
    @Schema(description = "참고 ID",
            example = "1000")
    private Long referenceId;
    @Schema(description = "읽음 유무",
            example = "true")
    private Boolean isRead;
    @Schema(
            description = "생성날짜"
    )
    LocalDateTime createTime;
    @Schema(
            description = "수정날짜"
    )
    LocalDateTime updateTime;
}
