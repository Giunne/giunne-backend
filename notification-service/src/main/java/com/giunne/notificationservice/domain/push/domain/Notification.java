package com.giunne.notificationservice.domain.push.domain;

import com.giunne.notificationservice.domain.push.domain.type.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class Notification {
    private Long id;
    private Long targetId;
    private Long senderId;
    @Builder.Default
    private String title = "";
    @Builder.Default
    private String content = "";
    private NotificationType notificationType;
    private Long referenceId;
    private Long recreationId;
    @Builder.Default
    private Boolean isRead = false;
}
