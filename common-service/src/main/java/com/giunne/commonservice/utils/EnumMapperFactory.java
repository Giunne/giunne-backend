package com.giunne.commonservice.utils;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.domain.common.EnumMapperValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class EnumMapperFactory {

    // 다양한 종류의 Enum을 생성 및 관리하는 factory
    private final Map<String, List<EnumMapperValue>> factory = new LinkedHashMap<>();

    // 새로운 Enum 종류를 추가하는 함수
    public void put(String key, Class<? extends EnumMapperType> e){
        factory.put(key, toEnumValues(e));
    }

    // Enum의 내용들을 List로 바꾸어주는 함수
    private List<EnumMapperValue> toEnumValues(Class<? extends EnumMapperType> e) { // EnumMapperType 인터페이스 구현체만 오도록 제한
        return Arrays.stream(e.getEnumConstants())
                .map(EnumMapperValue::new)
                .collect(Collectors.toList());
    }

    // 특정 Enum의 항목들을 조회하는 함수
    public List<EnumMapperValue> get(String key){
        return factory.get(key);
    }

    public Map<String, List<EnumMapperValue>> get(List<String> keys){
        if (keys == null || keys.isEmpty()) {
            return new LinkedHashMap<>();
        }

        return keys.stream().collect(Collectors.toMap(Function.identity(), factory::get));

    }

    public Map<String, List<EnumMapperValue>> getAll(){return factory;}

}