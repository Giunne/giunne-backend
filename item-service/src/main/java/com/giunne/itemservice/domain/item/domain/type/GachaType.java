package com.giunne.itemservice.domain.item.domain.type;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Getter
public enum GachaType {
    GENERAL("일반", 100L, new LinkedHashMap<>() {{
        put(ItemGrade.S, 10);
        put(ItemGrade.A, 20);
        put(ItemGrade.B, 30);
        put(ItemGrade.C, 40);
    }}
    ),

    PREMIUM("고급", 200L, new LinkedHashMap<>() {{
        put(ItemGrade.A, 10);
        put(ItemGrade.B, 40);
        put(ItemGrade.C, 50);
    }}
    );

    private final String type;
    private final Long price;
    private final Map<ItemGrade, Integer> itemGradeMap;

    GachaType(String type, Long price, Map<ItemGrade, Integer> itemGradeMap) {
        this.type = type;
        this.price = price;
        this.itemGradeMap = itemGradeMap;
    }

    public static GachaType from(String type) {
        validate(type);
        return GachaType.valueOf(type.toUpperCase());
    }

    public static boolean isItemGrad(String type) {
        List<GachaType> roles = Arrays.stream(GachaType.values())
                .filter(r -> r.name().equals(type))
                .toList();

        return !roles.isEmpty();
    }

    private static void validate(String role) {
        if (!GachaType.isItemGrad(role.toUpperCase())) {
            throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
        }
    }

}
