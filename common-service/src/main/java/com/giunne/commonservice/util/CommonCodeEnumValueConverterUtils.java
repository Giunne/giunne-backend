package com.giunne.commonservice.util;

import com.giunne.commonservice.type.CommonCodeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

/**
 * Enum과 legacy code간 상호 변환 유틸리티 클래스
 * 공통적 Enum {@Method ofLacyCode} 메서드 통합
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonCodeEnumValueConverterUtils {

    // code -> Enum 변환
    public static <T extends Enum<T> & CommonCodeType> T ofCommonCode(Class<T> enumClass, String code) {
        /**
         *  1.DTO 내부 Enum 타입으로 변환간 NULL, "'" 값 체크를 하면 되지만,
         *  2. 필터링 Dto(SearchType)에서 "" 값이 들어올수 있기 때문에 NULL 반환
         *  '1'를 통해 NULL 이여도 Converter를 통해 검증이 이루어져 DB에는 NULL or "" 들어가지 않음.
         */

        if (StringUtils.hasText(code)) {
            return null;
        }

        return EnumSet.allOf(enumClass).stream()
                .filter(e -> e.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("enum=[%s]에 Code=[%s]가 존재하지 않습니다.",
                        enumClass.getName(), code)));
    }

    // Enum -> code
    public static <T extends Enum<T> & CommonCodeType>String toCode(T enumValue){
        return enumValue.getCode();
    }
}
