package com.giunne.commonservice.domain.auth;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.domain.auth.exception.InvalidOAuthTypeException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * OAuth 구분
 */

@Getter
public enum OAuthType {

    GIUNNE("기운내"),
    KAKAO("카카오"),
    ;

    private final String description;

    OAuthType(String description) {
        this.description = description;
    }

    public static OAuthType from(String oautType) {
        validate(oautType);
        return OAuthType.valueOf(oautType.toUpperCase());
    }

    public static boolean isOAuthType(String oautType) {
        List<OAuthType> states = Arrays.stream(OAuthType.values())
                .filter(memberState -> memberState.name().equals(oautType))
                .toList();

        return !states.isEmpty();
    }

    private static void validate(String oautType) {
        if (!OAuthType.isOAuthType(oautType.toUpperCase())) {
            throw new InvalidOAuthTypeException(ErrorCode.INVALID_OAUTHTYPE);
        }
    }

}
