package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;

import java.util.Arrays;

/**
 * 학교급구분
 */

public enum SchoolSe implements EnumMapperType {

    ELEMENTARY_SCHOOL("001", "초등학교"),
    MIDDLE_SCHOOL("002", "중학교"),
    HIGH_SCHOOL("003", "고등학교"),
    ;

    private final String code;
    private final String codeName;

    SchoolSe(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static SchoolSe from(String code) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(code))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_GENDER)
                );
    }

    public static SchoolSe toSchoolSe(String codeName) {
        return Arrays.stream(values())
                .filter(item -> item.codeName.equals(codeName))
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
