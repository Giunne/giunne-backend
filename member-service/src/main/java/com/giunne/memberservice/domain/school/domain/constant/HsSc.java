package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;
import lombok.Getter;

/**
 * 고등학교구분명
 * 고등학교의 교육과정에 따른 구분
 */
@Getter
public enum HsSc implements CommonCodeType {

    SPECIALIZED_HIGH_SCHOOL("22", "특성화고"),
    AUTONOMOUS_HIGH_SCHOOL("33", "자율고"),
    SPECIAL_HIGH_SCHOOL("44", "특목고"),
    ETC("99", "기타");


    private final String code;
    private final String codeName;

    HsSc(String code, String codeName) {
        this.code = code;
        this.codeName = codeName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getDesc() {
        return this.codeName;
    }

}


