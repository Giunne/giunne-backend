package com.giunne.memberservice.domain.recreation.application.interfaces;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.application.dto.request.GetRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.GetRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.domain.Recreation;

public interface RecreationRepository {
    Recreation createRecreation(Recreation recreation);
    PaginationModel<GetRecreationResponseDto> findRecreation(GetRecreationRequestDto dto);
    PaginationModel<GetRecreationResponseDto> getMyRecreation(Member member, Pageable dto);
    Recreation findById(Long id);
    Recreation findByRecreationCode(String recreationCode);
}
