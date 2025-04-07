package com.giunne.notificationservice.domain.push.repository.entity;

import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.notificationservice.domain.push.domain.Notification;
import com.giunne.commonservice.domain.notification.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="notification")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no")
    private Long id;
    private Long targetId;
    private Long senderId;
    private String title;
    private String content;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;
    private Long referenceId;
    private Long recreationId;
    private Boolean isRead;

    public NotificationEntity(Notification notification){
        this.id = notification.getId();
        this.targetId = notification.getTargetId();
        this.senderId = notification.getSenderId();
        this.title = notification.getTitle();
        this.content = notification.getContent();
        this.notificationType = notification.getNotificationType();
        this.referenceId = notification.getReferenceId();
        this.isRead = notification.getIsRead();
        this.recreationId = notification.getRecreationId();
    }

    public Notification toDomain() {
        return Notification.builder()
                .id(id)
                .targetId(targetId)
                .senderId(senderId)
                .title(title)
                .content(content)
                .notificationType(notificationType)
                .referenceId(referenceId)
                .isRead(isRead)
                .recreationId(recreationId)
                .build();
    }

}
