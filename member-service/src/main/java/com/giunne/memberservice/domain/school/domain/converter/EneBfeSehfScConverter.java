package com.giunne.memberservice.domain.school.domain.converter;

import com.giunne.commonservice.type.converter.AbstracEnumAttributeConverter;
import com.giunne.memberservice.domain.school.domain.constant.EneBfeSehfSc;
import jakarta.persistence.Converter;

@Converter
public class EneBfeSehfScConverter extends AbstracEnumAttributeConverter<EneBfeSehfSc> {

    public static final String ENUM_NAME = "입시전후기구분명";

    public EneBfeSehfScConverter() {
        super(EneBfeSehfSc.class, ENUM_NAME);
    }
}
