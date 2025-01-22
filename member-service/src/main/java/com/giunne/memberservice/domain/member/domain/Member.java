package com.giunne.memberservice.domain.member.domain;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.auth.OAuthType;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.type.*;
import com.giunne.memberservice.domain.school.domain.School;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class Member {
    private Long id; // 회원 번호
    private School school; // 학교 번호
    private LoginId loginId; // 로그인 ID
    private Password password; // 로그임 패스워드
    private UserName userName; // 회원명
    private Nickname nickname; // 닉네임
    private Birth birth; // 생년월일
    private Gender gender; // 성별 구분
    private Phone phone; // 휴대폰 번호
    private Email email; // 이메일
    private MemberRole role;
    private String refreshToken; // 리프레시토큰
    private LocalDateTime refreshTokenExpirationTime; // 리프레시토큰 만료날짜
    private OAuthType oAuthType; // OAuth 구분
}
