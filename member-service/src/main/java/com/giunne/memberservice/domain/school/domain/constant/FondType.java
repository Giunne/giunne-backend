package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.type.CommonCodeType;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 설립형태
 * 학교의 설립주체에 따른 구분
 */
@Getter
public enum FondType implements EnumMapperType {

    PUBLIC("PUBLIC", "공립"),
    ESTABLISHMENT("ESTABLISHMENT", "국립"),
    PRIVATE("PRIVATE", "사립"),
    ;

    private final String code;
    private final String codeName;

    FondType(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static FondType from(String fondTypeCode) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(fondTypeCode))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_FONDTYPE)
                );
    }

    public static FondType toFondType(String codeName) {
        return Arrays.stream(values())
                .filter(item -> item.codeName.equals(codeName))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_FONDTYPE)
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
