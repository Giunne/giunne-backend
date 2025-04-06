package com.giunne.memberservice.domain.notice.application;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.application.AvatarService;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.notice.application.dto.request.CreateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.request.UpdateNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.application.interfaces.NoticeRepository;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {
    private final NoticeRepository noticeRepository;
    private final RecreationService recreationService;
    private final AvatarService avatarService;

    @Transactional
    public void saveNotice(MemberPrincipal memberPrincipal, CreateNoticeRequestDto dto) {
        if (memberPrincipal.getPlayerId() == null) {
            throw new IllegalArgumentException("아바타 정보가 없습니다.");
        }
        if (memberPrincipal.getRole() != MemberRole.ROLE_TEACHER) {
            throw new IllegalArgumentException("선생님이 아닙니다.");
        }
        Recreation recreation = recreationService.getRecreation(dto.recreationId());
        Avatar avatar = avatarService.getAvatar(memberPrincipal.getPlayerId());
        Notice notice = Notice.builder()
                .title(dto.title())
                .content(dto.content())
                .recreation(recreation)
                .writer(avatar)
                .build();
        noticeRepository.saveNotice(notice);
    }

    @Transactional
    public void deleteById(MemberPrincipal memberPrincipal, Long id) {
        Notice notice = noticeRepository.getNotice(id);
        if (!Objects.equals(notice.getWriter().getId(), memberPrincipal.getPlayerId())) {
            throw new IllegalArgumentException("작성자가 아닙니다.");
        }
        noticeRepository.deleteById(id);
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

    public PaginationModel<GetNoticeResponseDto> getNoticeList(GetNoticeRequestDto dto) {
        return noticeRepository.getNoticeList(dto);
    }

    public GetNoticeResponseDto getNotice(Long id) {
        Notice notice = noticeRepository.getNotice(id);
        return GetNoticeResponseDto.builder()
                .id(notice.getId())
                .writerId(notice.getWriter().getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .createTime(notice.getCreatedTime())
                .updateTime(notice.getUpdatedTime())
                .build();
    }

}
