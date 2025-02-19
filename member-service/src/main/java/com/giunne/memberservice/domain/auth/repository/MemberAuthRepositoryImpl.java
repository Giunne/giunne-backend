package com.giunne.memberservice.domain.auth.repository;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.AuthenticationException;
import com.giunne.commonservice.jwt.constant.TokenType;
import com.giunne.commonservice.jwt.dto.JwtTokenDto;
import com.giunne.commonservice.jwt.service.TokenManager;
import com.giunne.commonservice.utils.RedisUtil;
import com.giunne.memberservice.domain.auth.application.interfaces.MemberAuthRepository;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import com.giunne.memberservice.domain.auth.repository.entity.MemberAuthEntity;
import com.giunne.memberservice.domain.auth.repository.jpa.JpaMemberAuthRepository;
import com.giunne.memberservice.domain.member.application.interfaces.MemberRepository;
import com.giunne.memberservice.domain.member.domain.Member;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Repository
@RequiredArgsConstructor
public class MemberAuthRepositoryImpl implements MemberAuthRepository {

    private final JpaMemberAuthRepository jpaMemberAuthRepository;
    private final MemberRepository memberRepository;
    private final TokenManager tokenManager;
    private final RedisUtil redisUtil;

    @Override
    @Transactional
    public MemberAuth registerMember(MemberAuth memberAuth, Member member) {
        Member saveMember = memberRepository.save(member);
        MemberAuthEntity memberAuthEntity = MemberAuthEntity.builder()
                .memberAuth(memberAuth)
                .memberId(saveMember.getId())
                .build();
        MemberAuthEntity saveMemberAuthEntity = jpaMemberAuthRepository.save(memberAuthEntity);

        return saveMemberAuthEntity.toMemberAuth();
    }

    @Override
    @Transactional
    public MemberAuth loginMember(String loginId, String password) {
        MemberAuthEntity memberAuthEntity = jpaMemberAuthRepository.findByLoginId(loginId).orElseThrow();

        MemberAuth memberAuth = memberAuthEntity.toMemberAuth();

        if (!memberAuth.matchPassword(password)) {
            throw new IllegalArgumentException("Invalid password");
        }

        memberAuthEntity.updateLastLoginAt();
        return memberAuth;
    }

    @Override
    @Transactional
    public void updateRefreshToken(String loginId, JwtTokenDto jwtTokenDto) {
        MemberAuthEntity memberAuthEntity = jpaMemberAuthRepository.findByLoginId(loginId).orElseThrow();
        memberAuthEntity.updateRefreshToken(jwtTokenDto);
    }

    @Override
    public MemberAuth findByRefreshToken(String refreshToken) {
        MemberAuthEntity memberAuthEntity = jpaMemberAuthRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AuthenticationException(ErrorCode.REFRESH_TOKEN_NOT_FOUND));
        LocalDateTime tokenExpirationTime = memberAuthEntity.getRefreshTokenExpirationTime();
        if (tokenExpirationTime.isBefore(LocalDateTime.now())) {
            throw new AuthenticationException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        return memberAuthEntity.toMemberAuth();
    }

    @Override
    public void logout(String accessToken) {
        // 토큰 검증
        tokenManager.validateToken(accessToken);

        // 토큰 타입 확인
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        String tokenType = tokenClaims.getSubject();
        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        // refresh token 만료 처리
        Long memberId = Long.valueOf((Integer) tokenClaims.get("memberId"));
        MemberAuthEntity memberAuthEntity = jpaMemberAuthRepository.findByMemberId(memberId)
                .orElseThrow(IllegalArgumentException::new);
        memberAuthEntity.expireRefreshToken(LocalDateTime.now());

        // redis에 black-list 등록
        Long tokenExpiration = tokenManager.getTokenExpiration(accessToken);
        redisUtil.setBlackList(accessToken, "access-token", tokenExpiration);
    }

}
