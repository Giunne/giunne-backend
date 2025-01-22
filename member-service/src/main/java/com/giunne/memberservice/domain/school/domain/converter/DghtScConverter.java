package com.giunne.memberservice.domain.school.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.memberservice.domain.school.domain.constant.DghtSc;
import jakarta.persistence.Converter;

@Converter
public class DghtScConverter extends AbstracEnumAttributeConverter<DghtSc> {

    public static final String ENUM_NAME = "주야구분명";

    public DghtScConverter() {
        super(DghtSc.class, ENUM_NAME);
    }
}
