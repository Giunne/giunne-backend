package com.giunne.notificationservice.domain.push.application;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.notificationservice.domain.push.application.dto.request.SendFcmMessageDto;
import com.giunne.commonservice.infra.external.domain.notification.client.dto.request.SendNotificationDto;
import com.giunne.notificationservice.domain.push.application.dto.response.GetNotificationResponseDto;
import com.giunne.notificationservice.domain.push.application.interfaces.NotificationRepository;
import com.giunne.notificationservice.domain.push.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final FirebaseMessageService firebaseMessageService;

    @Transactional
    public void saveNotification(SendNotificationDto dto) {
        Notification notification = Notification.builder()
                .targetId(dto.getTargetId())
                .senderId(dto.getSenderId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .notificationType(dto.getNotificationType())
                .referenceId(dto.getReferenceId())
                .referenceId(dto.getRecreationId())
                .build();


        notificationRepository.saveNotification(notification);

        SendFcmMessageDto sendFcmMessageDto = SendFcmMessageDto.builder()
                .memberId(notification.getTargetId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .build();
        firebaseMessageService.sendMessage(sendFcmMessageDto);
    }

    public void readAllNotificationByTargetId(MemberPrincipal memberPrincipal) {
        validateMemberPrincipal(memberPrincipal);
        notificationRepository.readAllNotificationByTargetId(memberPrincipal.getMemberId());
    }

    public void readNotificationById(MemberPrincipal memberPrincipal, Long id) {
        validateMemberPrincipal(memberPrincipal);
        notificationRepository.readNotificationById(id);
    }

    public Integer countNotReadNotification(MemberPrincipal memberPrincipal) {
        validateMemberPrincipal(memberPrincipal);
        return notificationRepository.countNotReadNotification(memberPrincipal.getMemberId());
    }

    public PaginationModel<GetNotificationResponseDto> getNotifications(MemberPrincipal memberPrincipal, Pageable dto) {
        validateMemberPrincipal(memberPrincipal);
        return notificationRepository.getNotifications(memberPrincipal.getMemberId(), dto);
    }

    private void validateMemberPrincipal(MemberPrincipal memberPrincipal) {
        if (memberPrincipal == null) {
            throw new IllegalArgumentException("회원정보가 없습니다.");
        }
    }

}
