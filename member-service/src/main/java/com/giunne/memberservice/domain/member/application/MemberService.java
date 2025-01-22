package com.giunne.memberservice.domain.member.application;

import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.school.application.SchoolService;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final SchoolService schoolService;

    public Member createUser(CreateTeacherAuthRequestDto dto) {
        School school = schoolService.getSchool(dto.schoolId());
        Member member = dto.toMember(school);
        return memberRepository.save(member);
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id);
    }

//    public GetUserResponseDto getUserProfile(Long id) {
//        Member member = getUser(id);
//        return new GetUserResponseDto(member);
//    }
}
