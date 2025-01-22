package com.giunne.commonservice.domain.common;

import lombok.Getter;

@Getter
public class EnumMapperValue {
    private final String code;
    private final String title;

    public EnumMapperValue(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.title = enumMapperType.getTitle();
    }

    @Override
    public String toString() {
        return "EnumMapperValue{" +
                "code='" + code + '\'' +
                ", title='" + title +
                '}';
    }
}
