package com.giunne.memberservice.domain.recreation.application.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.recreation.application.dto.request.GetRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.GetRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.domain.Recreation;

public interface RecreationRepository {
    Recreation createRecreation(Recreation recreation);
    PaginationModel<GetRecreationResponseDto> findRecreation(GetRecreationRequestDto dto);
}
