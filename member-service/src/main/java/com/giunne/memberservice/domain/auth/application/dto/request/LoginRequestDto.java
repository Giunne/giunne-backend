package com.giunne.memberservice.domain.auth.application.dto.request;

public record LoginRequestDto(
        String memberId
        , String password
) {
}
