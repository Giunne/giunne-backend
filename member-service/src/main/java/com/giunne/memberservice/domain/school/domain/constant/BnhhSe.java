package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.type.CommonCodeType;
import com.giunne.memberservice.domain.member.domain.constant.Gender;
import com.giunne.memberservice.domain.member.domain.exception.ErrorCode;
import com.giunne.memberservice.domain.member.domain.exception.InvalidGenderException;

import java.util.Arrays;

/**
 * 본교분교구분
 */
public enum BnhhSe implements EnumMapperType {
    MAIN_SCHOOL("MAIN_SCHOOL","본교"),
    BRANCH_SCHOOL("BRANCH_SCHOOL", "분교"),
    ;
    private final String code;
    private final String codeName;

    BnhhSe(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    public static BnhhSe from(String bnhhSeCode) {
        return Arrays.stream(values())
                .filter(item -> item.code.equals(bnhhSeCode))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_BNHHSE)
                );
    }

    public static BnhhSe toBnhhSe(String codeName) {
        return Arrays.stream(values())
                .filter(item -> item.codeName.equals(codeName))
                .findFirst()
                .orElseThrow(
                        () -> new InvalidGenderException(ErrorCode.INVALID_BNHHSE)
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

