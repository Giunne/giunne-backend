package com.giunne.memberservice.domain.auth.application.dto.request;

import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.type.*;
import com.giunne.memberservice.domain.school.domain.School;

public record CreateTeacherAuthRequestDto(
        String loginId
        , String password
        , String userName
        , String nickname
        , String birth
        , String phone
        , String email
        , Long schoolId
) {

    public Member toMember(School school) {
        return Member.builder()
                .loginId(LoginId.from(loginId))
                .password(Password.createEncryptedPassword(password))
                .userName(UserName.from(userName))
                .nickname(Nickname.from(nickname))
                .birth(Birth.from(birth))
                .phone(Phone.from(phone))
                .email(Email.from(email))
                .school(school)
                .build();
    }

}
