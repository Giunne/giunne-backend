package com.giunne.memberservice.domain.auth.application.dto.request;

import com.giunne.commonservice.domain.auth.MemberRole;
import com.giunne.commonservice.domain.auth.OAuthType;
import com.giunne.memberservice.domain.member.domain.Member;
import com.giunne.memberservice.domain.member.domain.type.*;
import com.giunne.memberservice.domain.school.domain.School;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "선생님 회원가입 요청DTO")
public record CreateTeacherAuthRequestDto(
        @Schema(
                description = "로그인 ID",
                example = "test"
        )
        String loginId,
        @Schema(
                description = "로그인 패스워드",
                example = "123***"
        )
        String password,
        @Schema(
                description = "이름",
                example = "김싸피"
        )
        String userName,
        @Schema(
                description = "닉네임",
                example = "김싸피"
        )
        String nickname,
        @Schema(
                description = "생년월일",
                example = "2025-01-01"
        )
        String birth,
        @Schema(
                description = "연락처",
                example = "010-1234-5678"
        )
        String phone,
        @Schema(
                description = "이메일",
                example = "email@email.com"
        )
        String email,
        @Schema(
                description = "학교ID",
                example = "1000"
        )
        Long schoolId
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
                .role(MemberRole.ROLE_TEACHER)
                .oAuthType(OAuthType.GIUNNE)
                .build();
    }

}
