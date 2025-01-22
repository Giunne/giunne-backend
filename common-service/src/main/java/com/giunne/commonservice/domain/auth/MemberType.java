package com.giunne.commonservice.domain.auth;

import java.util.Arrays;
import java.util.List;

public enum MemberType {

    KAKAO,
    NAVER
    ;

    public static MemberType from(String type) {
        return MemberType.valueOf(type.toUpperCase());
    }

    public static boolean isMemberType(String type) {
        List<MemberType> memberTypes = Arrays.stream(MemberType.values())
                .filter(memberType -> memberType.name().equals(type))
                .toList();
        return !memberTypes.isEmpty();
    }

}

