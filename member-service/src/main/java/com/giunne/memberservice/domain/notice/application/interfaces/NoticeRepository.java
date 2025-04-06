package com.giunne.memberservice.domain.notice.application.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.notice.application.dto.request.GetNoticeRequestDto;
import com.giunne.memberservice.domain.notice.application.dto.response.GetNoticeResponseDto;
import com.giunne.memberservice.domain.notice.domain.Notice;

public interface NoticeRepository {
    Notice saveNotice(Notice notice);

    Notice getNotice(Long id);

    void deleteById(Long id);

    Notice updateNotice(Notice notice);

    PaginationModel<GetNoticeResponseDto> getNoticeList(GetNoticeRequestDto dto);
}
