package com.giunne.notificationservice.domain.push.application.dto.request;

import com.giunne.notificationservice.domain.push.domain.Notification;
import com.giunne.notificationservice.domain.push.domain.type.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationDto {

    @Schema(description = "송신자 회원 ID",
            example = "1")
    private Long targetId;

    @Schema(description = "수신자 회원 ID",
            example = "1")
    private Long senderId;

    @Schema(description = "메시지 제목",
            example = "메시지 제목")
    private String title;

    @Schema(description = "메시지 내용",
            example = "메시지 내용")
    private String content;

    @Schema(description = "메시지 타입")
    private NotificationType notificationType;

    @Schema(description = "참조 ID",
            example = "1")
    private Long referenceId;

    @Schema(description = "레크레이션 ID",
            nullable = true)
    private Long recreationId;

    public Notification toNotification() {
        return Notification.builder()
                .targetId(targetId)
                .senderId(senderId)
                .title(title)
                .content(content)
                .notificationType(notificationType)
                .referenceId(referenceId)
                .referenceId(recreationId)
                .build();
    }
}
