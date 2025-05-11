package com.giunne.memberservice.domain.notice.application;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.notification.NotificationTemplate;
import com.giunne.commonservice.domain.notification.NotificationType;
import com.giunne.commonservice.infra.external.domain.notification.client.NotificationInfoClient;
import com.giunne.commonservice.infra.external.domain.notification.client.dto.request.SendNotificationDto;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.application.AvatarService;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.notice.application.dto.request.CreateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.UpdateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNotReadNoticeCountDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.application.interfaces.NoticeRepository;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final RecreationService recreationService;
    private final AvatarService avatarService;
    private final NotificationInfoClient notificationInfoClient;

    @Transactional
    public void saveNotice(MemberPrincipal memberPrincipal, CreateNoticeRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        if (memberPrincipal.getRole() != MemberRole.ROLE_TEACHER) {
            throw new IllegalArgumentException("선생님이 아닙니다.");
        }
        Recreation recreation = recreationService.getRecreation(dto.recreationId());
        Avatar writer = avatarService.getAvatar(memberPrincipal.getPlayerId());
        Notice notice = Notice.builder()
                .title(dto.title())
                .content(dto.content())
                .recreation(recreation)
                .writer(writer)
                .build();
        Notice saveNotice = noticeRepository.saveNotice(notice);

        List<Avatar> allAvatarByRecreationId = avatarService.getAllAvatarByRecreationId(recreation.getId());
        noticeRepository.initNoticeRead(saveNotice, allAvatarByRecreationId);

        for (Avatar studentAvatar : allAvatarByRecreationId) {

            if (studentAvatar.getId().equals(memberPrincipal.getPlayerId())) {
                continue;
            }

            SendNotificationDto notificationDto = SendNotificationDto.builder()
                    .targetId(
                            studentAvatar.getMember().getId()
                    )
                    .senderId(memberPrincipal.getMemberId())
                    .title("기운내 프로젝트")
                    .content(NotificationTemplate.TEACHER_NOTICE.getTemplate())
                    .notificationType(NotificationType.TEACHER_NOTICE)
                    .build();

            try {
                notificationInfoClient.sendMessage(notificationDto);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }

    @Transactional
    public void deleteById(MemberPrincipal memberPrincipal, Long id) {
        Notice notice = noticeRepository.getNotice(id);
        if (!Objects.equals(notice.getWriter().getId(), memberPrincipal.getPlayerId())) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        noticeRepository.deleteNoticeReadByNoticeId(id);
        noticeRepository.deleteById(id);
    }

    @Transactional
    public void deleteByAvatar(Avatar avatar) {
        noticeRepository.deleteNoticeReadByWriterId(avatar.getId());
        noticeRepository.deleteNoticeByWriterId(avatar.getId());
    }

    @Transactional
    public void updateNotice(MemberPrincipal memberPrincipal, UpdateNoticeRequestDto dto) {
        Notice notice = noticeRepository.getNotice(dto.id());
        if (!Objects.equals(notice.getWriter().getId(), memberPrincipal.getPlayerId())) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        notice.updateNoticeInfo(dto.title(), dto.content());
        noticeRepository.updateNotice(notice);
    }

    public PaginationModel<GetNoticeResponseDto> getNoticeList(MemberPrincipal memberPrincipal, GetNoticeRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        Avatar avatar = avatarService.getAvatar(memberPrincipal.getPlayerId());
        return noticeRepository.getNoticeList(dto, avatar);
    }

    public GetNoticeResponseDto getNotice(MemberPrincipal memberPrincipal, Long id) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        Avatar avatar = avatarService.getAvatar(memberPrincipal.getPlayerId());
        return noticeRepository.getMyNotice(id, avatar);
    }

    @Transactional
    public void readNotice(MemberPrincipal memberPrincipal, Long id) {
        Notice notice = noticeRepository.getNotice(id);
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }

        Avatar avatar = avatarService.getAvatar(memberPrincipal.getPlayerId());
        noticeRepository.readNotice(notice, avatar);
    }

    public GetNotReadNoticeCountDto countNotReadNotice(MemberPrincipal memberPrincipal) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        Avatar avatar = avatarService.getAvatar(memberPrincipal.getPlayerId());

        Long count = noticeRepository.countNotReadNotice(avatar);

        return GetNotReadNoticeCountDto.builder()
                .count(count)
                .build();
    }

}
