package com.giunne.memberservice.domain.school.interfaces;

import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.school.api.request.GetSchoolPageRequestDto;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.giunne.memberservice.domain.school.domain.School;

import java.util.List;


public interface SchoolRepository {
    School save(School member);
    void saveAll(List<School> schoolList);
    School findBySchoolId(String schoolId);
    School findById(Long id);
    PaginationModel<GetSchoolPageResponseDto> findByNameLike(GetSchoolPageRequestDto dto);
}
