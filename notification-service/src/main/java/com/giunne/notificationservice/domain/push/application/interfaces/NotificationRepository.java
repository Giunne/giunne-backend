package com.giunne.notificationservice.domain.push.application.interfaces;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.notificationservice.domain.push.application.dto.response.GetNotificationResponseDto;
import com.giunne.notificationservice.domain.push.domain.Notification;

public interface NotificationRepository {

    void saveNotification(Notification notification);

    void readAllNotificationByTargetId(Long targetId);

    void readNotificationById(Long id);

    Integer countNotReadNotification(Long memberId);

    PaginationModel<GetNotificationResponseDto> getNotifications(Long memberId, Pageable dto);
}
