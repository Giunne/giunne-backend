package com.giunne.memberservice.domain.member.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 성별 구분
 */

@Getter
public enum Gender implements EnumMapperType {

    MALE("MALE", "남성"),
    FEMALE("FEMALE", "여성"),
    ;

    private final String code;
    private final String codeName;

    Gender(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static Gender from(String genderCode) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(genderCode))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_GENDER)
                );
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getTitle() {
        return codeName;
    }
}

