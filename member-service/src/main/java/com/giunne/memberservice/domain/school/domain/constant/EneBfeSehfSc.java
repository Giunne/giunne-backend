package com.giunne.memberservice.domain.school.domain.constant;

import com.giunne.commonservice.type.CommonCodeType;
import lombok.Getter;

/**
 * 입시전후기구분명
 * 고등학교 입시 모집 시기에 따른 구분
 */
@Getter
public enum EneBfeSehfSc implements CommonCodeType {

    BEFORE_AND_AFTER("001","전후기"),
    AFTER("002","후기"),
    BEFORE("003","전기"),
    ;

    private final String code;
    private final String codeName;

    EneBfeSehfSc(String code, String codeName) {
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

