package com.giunne.memberservice.domain.school.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.memberservice.domain.school.domain.constant.HsGnrlBusnsSc;
import jakarta.persistence.Converter;

@Converter
public class HsGnrlBusnsScConverter extends AbstracEnumAttributeConverter<HsGnrlBusnsSc> {

    public static final String ENUM_NAME = "고등학교일반전문구분명";

    public HsGnrlBusnsScConverter() {
        super(HsGnrlBusnsSc.class, ENUM_NAME);
    }
}
