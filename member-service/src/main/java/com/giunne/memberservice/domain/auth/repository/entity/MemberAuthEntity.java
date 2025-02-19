package com.giunne.memberservice.domain.auth.repository.entity;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.common.BaseEntity;
import com.giunne.commonservice.jwt.dto.JwtTokenDto;
import com.giunne.commonservice.util.DateTimeUtils;
import com.giunne.memberservice.domain.auth.domain.MemberAuth;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Entity
@Table(name = "member_auth")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberAuthEntity extends BaseEntity {
    @Id
    private String loginId;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private MemberRole role;
    private Long memberId;
    private LocalDateTime lastLoginDt;
    @Column(name = "refreshtoken", length = 500)
    private String refreshToken;
    @Column(name = "refreshtoken_expiration_time")
    private LocalDateTime refreshTokenExpirationTime;

    @Builder
    public MemberAuthEntity(MemberAuth memberAuth, Long memberId, String refreshToken, LocalDateTime refreshTokenExpirationTime) {
        this.loginId = memberAuth.getLoginId();
        this.password = memberAuth.getPassword();
        this.role = memberAuth.getRole();
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.refreshTokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }
    
    public MemberAuth toMemberAuth() {
        return MemberAuth.builder()
                .loginId(loginId)
                .password(password)
                .role(role)
                .memberId(memberId)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

    public void updateLastLoginAt() {
        this.lastLoginDt = LocalDateTime.now();
    }

    public void expireRefreshToken(LocalDateTime now) {
        this.refreshTokenExpirationTime = now;
    }

}
