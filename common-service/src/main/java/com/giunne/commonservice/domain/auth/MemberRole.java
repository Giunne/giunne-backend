package com.giunne.commonservice.domain.auth;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.AuthenticationException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum MemberRole {

    ROLE_STUDENT("STUDENT"),
    ROLE_TEACHER("TEACHER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_WITHDRAW("WITHDRAW")
    ;

    private final String role;

    MemberRole(String description) {
        this.role = description;
    }

    public static MemberRole from(String role) {
        validate(role);
        return MemberRole.valueOf(role.toUpperCase());
    }

    public static boolean isRole(String role) {
        List<MemberRole> roles = Arrays.stream(MemberRole.values())
                .filter(r -> r.name().equals(role))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if(!MemberRole.isRole(role.toUpperCase())) {
            throw new AuthenticationException(ErrorCode.INVALID_ROLE);
        }
    }

}

