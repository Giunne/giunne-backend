package com.giunne.memberservice.domain.school.application;


import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.school.api.request.GetSchoolPageRequestDto;
import com.giunne.memberservice.domain.school.api.response.GetSchoolPageResponseDto;
import com.giunne.memberservice.domain.school.domain.School;
import com.giunne.memberservice.domain.school.repository.SchoolRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepositoryImpl schoolRepository;

    public School getSchool(Long id) {
        return schoolRepository.findById(id);
    }

    public PaginationModel<GetSchoolPageResponseDto> findByNameLike(GetSchoolPageRequestDto dto) {
        return schoolRepository.findByNameLike(dto);
    }
}
