package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;
import lombok.Getter;

/**
 * 고등학교일반전문구분명
 * 고등학교의 일반계 또는 전문계에 따른 구분
 */
@Getter
public enum HsGnrlBusnsSc implements CommonCodeType {

    GENERAL("12","일반계"),
    PROFESSIONAL("13","전문계"),
    ETC("14","기타");

    private final String code;
    private final String codeName;

    HsGnrlBusnsSc(String code, String codeName) {
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

