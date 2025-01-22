package com.giunne.commonservice.utils;

import com.giunne.commonservice.domain.common.EnumMapperType;
import com.giunne.commonservice.domain.common.EnumMapperValue;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EnumMapper {
    private final Map<String, List<EnumMapperValue>> factory = new LinkedHashMap<>();

    public EnumMapper() {}

    public void put(String key, Class<? extends EnumMapperType> e){
        factory.put(key, toEnumValues(e));
    }

    private List<EnumMapperValue> toEnumValues(Class<? extends EnumMapperType> e) { // EnumMapperType 인터페이스 구현체만 오도록 제한

        return Arrays.stream(e.getEnumConstants())
                .map(EnumMapperValue::new)
                .collect(Collectors.toList());
    }

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