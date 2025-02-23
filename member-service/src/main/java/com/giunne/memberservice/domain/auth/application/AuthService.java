package com.giunne.memberservice.domain.auth.application;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.jwt.constant.GrantType;
import com.giunne.commonservice.jwt.dto.JwtTokenDto;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.memberservice.domain.auth.application.dto.request.AccessTokenRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateStudentAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.CreateTeacherAuthRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.request.LoginRequestDto;
import com.giunne.memberservice.domain.auth.application.dto.response.AccessTokenResponseDto;
import com.giunne.memberservice.domain.auth.application.dto.response.MemberAccessTokenResponseDto;
import com.giunne.memberservice.domain.auth.application.interfaces.MemberAuthRepository;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.type.Password;
import com.giunne.memberservice.domain.recreation.application.RecreationService;
import com.giunne.memberservice.domain.recreation.domain.Recreation;
import com.giunne.memberservice.domain.school.application.SchoolService;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final SchoolService schoolService;
    private final MemberAuthRepository memberAuthRepository;
    private final TokenManager tokenManager;
    private final RecreationService recreationService;

    public MemberAccessTokenResponseDto registerTeacher(CreateTeacherAuthRequestDto dto) {
        School school = schoolService.getSchool(dto.schoolId());

        memberRepository.validateDuplicateMember(dto.loginId());
        MemberAuth memberAuth = MemberAuth.builder()
                .loginId(dto.loginId())
                .password(Password.createEncryptedPassword(dto.password()).getPassword())
                .role(MemberRole.ROLE_TEACHER)
                .build();

        Member member = dto.toMember(school);
        memberAuth = memberAuthRepository.registerMember(memberAuth, member);
        JwtTokenDto jwtToken = tokenManager.createJwtTokenDto(memberAuth.getMemberId(), null, memberAuth.getRole());
        memberAuthRepository.updateRefreshToken(memberAuth.getLoginId(), jwtToken);

        return MemberAccessTokenResponseDto.of(jwtToken, memberAuth.getRole());
    }

    public MemberAccessTokenResponseDto registerStudent(CreateStudentAuthRequestDto dto) {
        School school = schoolService.getSchool(dto.schoolId());

        memberRepository.validateDuplicateMember(dto.loginId());
        MemberAuth memberAuth =  MemberAuth.builder()
                .loginId(dto.loginId())
                .password(dto.password())
                .role(MemberRole.ROLE_STUDENT)
                .build();

        Member member = dto.toMember(school);

        Recreation byRecreationCode = recreationService.findByRecreationCode(dto.recreationCode());
        if(byRecreationCode == null) {
            throw new IllegalArgumentException("존재하지 않는 코드입니다.");
        }
        memberAuth = memberAuthRepository.registerMember(memberAuth, member);

        JwtTokenDto jwtToken = tokenManager.createJwtTokenDto(memberAuth.getMemberId(), null, memberAuth.getRole());
        memberAuthRepository.updateRefreshToken(memberAuth.getLoginId(), jwtToken);

        return  MemberAccessTokenResponseDto.of(jwtToken, memberAuth.getRole());
    }

    public MemberAccessTokenResponseDto loginMember(LoginRequestDto loginRequestDto) {
        MemberAuth memberAuth = memberAuthRepository.loginMember(loginRequestDto.loginId(), loginRequestDto.password());
        JwtTokenDto jwtToken = tokenManager.createJwtTokenDto(memberAuth.getMemberId(), null, memberAuth.getRole());
        memberAuthRepository.updateRefreshToken(memberAuth.getLoginId(), jwtToken);

        return MemberAccessTokenResponseDto.of(jwtToken, memberAuth.getRole());
    }


    public void logout(String accessToken) {
        memberAuthRepository.logout(accessToken);
    }


    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken, AccessTokenRequestDto accessTokenRequestDto) {
        MemberAuth memberAuth = memberAuthRepository.findByRefreshToken(refreshToken);
        Date accessTokenExpireTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(memberAuth.getMemberId(), accessTokenRequestDto.getPlayerId(), memberAuth.getRole(), accessTokenExpireTime);

        return AccessTokenResponseDto.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }

}
