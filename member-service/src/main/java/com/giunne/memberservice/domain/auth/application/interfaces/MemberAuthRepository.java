package com.giunne.memberservice.domain.auth.application.interfaces;

import com.giunne.commonservice.jwt.dto.JwtTokenDto;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import com.giunne.memberservice.domain.member.domain.Member;

public interface MemberAuthRepository {

    // 회원 등록
    MemberAuth registerMember(MemberAuth userAuth, Member member);
    // 로그인
    MemberAuth loginMember(String loginId, String password);

    void updateRefreshToken(String loginId, JwtTokenDto jwtTokenDto);

    MemberAuth findByRefreshToken(String refreshToken);

    void logout(String accessToken);
}
