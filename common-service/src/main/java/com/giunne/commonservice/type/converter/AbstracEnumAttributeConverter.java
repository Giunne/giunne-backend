package com.giunne.commonservice.type.converter;

import com.giunne.commonservice.type.CommonCodeType;
import com.giunne.commonservice.util.CommonCodeEnumValueConverterUtils;
import jakarta.persistence.AttributeConverter;
import lombok.Getter;

@Getter
public class AbstracEnumAttributeConverter<E extends Enum<E> & CommonCodeType> implements AttributeConverter<E, String> {

    /**
     * 대상 Enum 클래스 객체{@link Class}
     */

    private Class<E> targetEnumClass;

    /**
     * {@NotNulZ Enum} Enum에 대한 오류 메시지 출력에 사용
     */
    private String enumName;

    public AbstracEnumAttributeConverter(Class<E> element, String enumName) {
        this.targetEnumClass = element;
        this.enumName = enumName;
    }


    @Override
    public String convertToDatabaseColumn(E attribute) {
        //모든 Enum 타입 컬럼엔 null, "" 들어갈수 없음.
        if(attribute == null) {
            throw new IllegalArgumentException(String.format("[%s]는 NULL로 저장될 수 없습니다.", enumName));
        }

        return CommonCodeEnumValueConverterUtils.toCode(attribute);
    }

    @Override
    public E convertToEntityAttribute(String dbData) {
        return CommonCodeEnumValueConverterUtils.ofCommonCode(targetEnumClass, dbData);
    }
}

