package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;
import lombok.Getter;

/**
 * 주야구분명
 * 학교의 정규수업 시간에 따른 주간과 야간과정의 구분
 */
@Getter
public enum DghtSc implements CommonCodeType {

    DAYTIME("11", "주간"),
    NIGHT("12", "야간"),
    DAY_AND_NIGHT("13", "주야간");

    private final String code;
    private final String codeName;

    DghtSc(String code, String codeName) {
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

