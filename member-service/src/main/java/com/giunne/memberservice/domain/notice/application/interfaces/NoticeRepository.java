package com.giunne.memberservice.domain.notice.application.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.avatar.domain.Avatar;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.domain.Notice;
import com.giunne.memberservice.domain.recreation.domain.Recreation;

import java.util.List;

public interface NoticeRepository {
    Notice saveNotice(Notice notice);

    Notice getNotice(Long id);

    void deleteById(Long id);

    Notice updateNotice(Notice notice);

    PaginationModel<GetNoticeResponseDto> getNoticeList(GetNoticeRequestDto dto, Avatar avatar);

    void initNoticeRead(Notice notice, List<Avatar> avatars);

    void readNotice(Notice notice, Avatar avatar);

    GetNoticeResponseDto getMyNotice(Long id, Avatar avatar);

    Long countNotReadNotice(Avatar avatar);

    void deleteNoticeReadByNoticeId(Long noticeId);

    void singUpNoticeRead(Recreation recreation, Avatar avatar);
}
