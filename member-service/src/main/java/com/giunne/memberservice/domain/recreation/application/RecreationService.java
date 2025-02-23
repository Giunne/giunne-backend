package com.giunne.memberservice.domain.recreation.application;

import com.giunne.commonservice.domain.common.Pageable;
import com.giunne.commonservice.principal.MemberPrincipal;
import com.giunne.commonservice.ui.PaginationModel;
import com.giunne.memberservice.domain.member.application.MemberService;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.recreation.application.dto.request.CreateRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.request.GetRecreationRequestDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.CreateRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.application.dto.response.GetRecreationResponseDto;
import com.giunne.memberservice.domain.recreation.application.interfaces.RecreationRepository;
import com.giunne.memberservice.domain.recreation.domain.RandomCodeGenerator;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.recreation.domain.type.BaseNumber;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationCode;
import com.giunne.memberservice.domain.recreation.domain.type.RecreationName;
import com.giunne.memberservice.domain.school.application.SchoolService;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecreationService {

    private final RecreationRepository recreationRepository;
    private final MemberService memberService;
    private final SchoolService schoolService;

    public CreateRecreationResponseDto createRecreation(MemberPrincipal memberPrincipal, CreateRecreationRequestDto dto) {
        Member member = memberService.getMember(memberPrincipal.getMemberId());
        School school = schoolService.getSchool(member.getSchool().getId());

        Recreation recreation = Recreation.builder()
                .recreationName(RecreationName.from(dto.recreationName()))
                .recreationCode(RecreationCode.from(RandomCodeGenerator.generateCode()))
                .baseNumber(BaseNumber.from(dto.baseNumber()))
                .school(school)
                .member(member)
                .build();

        Recreation repositoryRecreation = recreationRepository.createRecreation(recreation);
        return CreateRecreationResponseDto.builder()
                .id(repositoryRecreation.getId())
                .recreationCode( repositoryRecreation.getRecreationCode().getRecreationCode())
                .recreationName(repositoryRecreation.getRecreationName().getRecreationName())
                .baseNumber(repositoryRecreation.getBaseNumber().getBaseNumber())
                .build();
    }

    public PaginationModel<GetRecreationResponseDto> getRecreation(MemberPrincipal memberPrincipal, GetRecreationRequestDto dto) {
        Member member = memberService.getMember(memberPrincipal.getMemberId());
        School school = schoolService.getSchool(member.getSchool().getId());

        return recreationRepository.findRecreation(dto);
    }

    public Recreation getRecreation(Long id) {
        return recreationRepository.findById(id);
    }

    public PaginationModel<GetRecreationResponseDto> getMyRecreation(MemberPrincipal memberPrincipal, Pageable dto) {
        Member member = memberService.getMember(memberPrincipal.getMemberId());

        return recreationRepository.getMyRecreation(member, dto);
    }

    public Recreation findByRecreationCode(String recreationCode){
        return recreationRepository.findByRecreationCode(recreationCode);
    }
}
