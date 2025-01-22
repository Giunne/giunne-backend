package com.giunne.memberservice.domain.school.domain.constant;


import com.giunne.commonservice.type.CommonCodeType;
import lombok.Getter;

/**
 * 특수목적고등학교계열명
 * 특수목적고등학교에서 운영하는 교육과정의 유사한 학습 형태
 */
@Getter
public enum SpclyPurpsHsOrd implements CommonCodeType {
    ART("11", "예술계열"),
    FOREIGN_LANGUAGE("12", "외국어계열"),
    INDUSTRIAL_NEEDS("13", "산업수요 맞춤형 고등학교"),
    SCIENCE("14", "과학계열"),
    INTERNATIONAL("15", "국제계열"),
    ATHLETIC("15", "체육계열"),
    ETC("14","기타");

    private final String code;
    private final String codeName;

    SpclyPurpsHsOrd(String code, String codeName) {
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

