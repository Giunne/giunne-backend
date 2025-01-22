package com.giunne.memberservice.domain.auth.application.dto.response;

public record GetMemberResponseDto(
        Long id
        , String name
        , String profileImage
        , Integer followingCount
        , Integer followerCount) {

}
