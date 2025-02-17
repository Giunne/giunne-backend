package com.giunne.itemservice.domain.item.domain.type;

import com.giunne.commonservice.error.ErrorCode;
import com.giunne.commonservice.error.exception.BusinessException;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum GachaType {
    GENERAL("일반"),
    PREMIUM("고급")
    ;

    private final String type;

    GachaType(String description) {
        this.type = description;
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
        if(!GachaType.isItemGrad(role.toUpperCase())) {
            throw new BusinessException(ErrorCode.PROFILE_SIZE_LIMIT);
        }
    }
}
