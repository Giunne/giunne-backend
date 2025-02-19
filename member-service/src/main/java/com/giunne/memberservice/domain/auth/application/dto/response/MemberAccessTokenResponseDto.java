package com.giunne.memberservice.domain.auth.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.jwt.dto.JwtTokenDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberAccessTokenResponseDto {
    private String grantType;

    private String accessToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

    private String refreshToken;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date refreshTokenExpireTime;

    private MemberRole role;

    public static MemberAccessTokenResponseDto of(JwtTokenDto jwtTokenDto, MemberRole role) {
        return MemberAccessTokenResponseDto.builder()
                .grantType(jwtTokenDto.getGrantType())
                .accessToken(jwtTokenDto.getAccessToken())
                .accessTokenExpireTime(jwtTokenDto.getAccessTokenExpireTime())
                .refreshToken(jwtTokenDto.getRefreshToken())
                .refreshTokenExpireTime(jwtTokenDto.getRefreshTokenExpireTime())
                .role(role)
                .build();
    }
}
