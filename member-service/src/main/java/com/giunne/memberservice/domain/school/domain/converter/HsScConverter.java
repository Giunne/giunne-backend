package com.giunne.memberservice.domain.school.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.memberservice.domain.school.domain.constant.HsSc;
import jakarta.persistence.Converter;

@Converter
public class HsScConverter extends AbstracEnumAttributeConverter<HsSc> {

    public static final String ENUM_NAME = "고등학교구분명";

    public HsScConverter() {
        super(HsSc.class, ENUM_NAME);
    }
}
