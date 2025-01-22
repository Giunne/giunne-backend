package com.giunne.memberservice.domain.auth.application.dto.request;

import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.type.*;
import com.giunne.memberservice.domain.school.domain.School;

public record CreateStudentAuthRequestDto(
        String loginId
        , String password
        , String userName
        , String nickname
        , String birth
        , Long recreationId
) {

    public Member toMember(School school) {
        return Member.builder()
                .loginId(LoginId.from(loginId))
                .password(Password.createEncryptedPassword(password))
                .userName(UserName.from(userName))
                .nickname(Nickname.from(nickname))
                .birth(Birth.from(birth))
                .school(school)
                .build();
    }

}
