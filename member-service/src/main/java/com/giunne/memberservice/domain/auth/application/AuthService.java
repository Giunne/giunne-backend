package com.giunne.memberservice.domain.auth.application;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.LoginRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import com.giunne.memberservice.domain.auth.application.interfaces.MemberAuthRepository;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.school.application.SchoolService;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SchoolService schoolService;
    private final MemberAuthRepository memberAuthRepository;
    private final TokenManager tokenManager;

    public MemberAccessTokenResponseDto registerTeacher(CreateTeacherAuthRequestDto dto) {
        School school = schoolService.getSchool(dto.schoolId());

        memberRepository.validateDuplicateMember(dto.loginId());
        MemberAuth memberAuth =  MemberAuth.builder()
                .loginId(dto.loginId())
                .password(dto.password())
                .role(MemberRole.ROLE_TEACHER)
                .build();

        Member member = dto.toMember(school);
        memberAuth = memberAuthRepository.registerMember(memberAuth, member);

        return  createToken(memberAuth);
    }

//    public MemberAccessTokenResponseDto registerStudent(CreateStudentRequestDto dto) {
//        School school = schoolService.getSchool(dto.schoolId());
//
//        memberRepository.validateDuplicateMember(dto.loginId());
//        MemberAuth memberAuth =  MemberAuth.builder()
//                .loginId(dto.loginId())
//                .password(dto.password())
//                .role(MemberRole.ROLE_TEACHER)
//                .build();
//
//        Member member = dto.toMember(school);
//        memberAuth = memberAuthRepository.registerMember(memberAuth, member);
//
//        return  createToken(memberAuth);
//    }

    public MemberAccessTokenResponseDto loginMember(LoginRequestDto loginRequestDto) {
        MemberAuth memberAuth = memberAuthRepository.loginMember(loginRequestDto.memberId(), loginRequestDto.password());

        return createToken(memberAuth);
    }

    private MemberAccessTokenResponseDto createToken(MemberAuth memberAuth) {
        String token = tokenManager.createJwtTokenDto(memberAuth.getMemberId(), memberAuth.getRole()).getAccessToken();
        return new MemberAccessTokenResponseDto(token);
    }
}
